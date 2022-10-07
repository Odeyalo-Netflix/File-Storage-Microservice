package com.odeyalo.analog.netflix.support.events.handlers;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.image.size.ImageResizeData;
import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import com.odeyalo.analog.netflix.service.image.size.MultipleImageResizer;
import com.odeyalo.analog.netflix.support.events.Event;
import com.odeyalo.analog.netflix.support.events.OriginalImageSavedEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OriginalImageSavedEventHandler implements EventHandler {
    private final MultipleImageResizer resizer;
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(OriginalImageSavedEventHandler.class);
    private final TransactionTemplate template;

    private final List<ImageResizeData> DEFAULT_IMAGE_RESIZE_DATA = Arrays.asList(
            new ImageResizeData(100, 100),
            new ImageResizeData(200, 200),
            new ImageResizeData(300, 300),
            new ImageResizeData(400, 400)
    );

    @Autowired
    public OriginalImageSavedEventHandler(MultipleImageResizer resizer, ImageRepository imageRepository, TransactionTemplate template) {
        this.resizer = resizer;
        this.imageRepository = imageRepository;
        this.template = template;
    }

    @Override
    public void handleEvent(Event event) {
        if (!(event instanceof OriginalImageSavedEvent)) {
            this.logger.error("Wrong event received: {}, excepted: {}", event.getClass().getName(), OriginalImageSavedEvent.class.getName());
            return;
        }
        OriginalImageSavedEvent imageSavedEvent = (OriginalImageSavedEvent) event;
        String imageId = imageSavedEvent.getImageId();
        String originalImagePath = imageSavedEvent.getOriginalImagePath();
        BufferedImage bufferedImage = getBufferedImage(originalImagePath);
        List<Image> entities = this.resizer.resizeImage(bufferedImage, DEFAULT_IMAGE_RESIZE_DATA)
                .stream()
                .map(this::getImage)
                .collect(Collectors.toList());
        template.executeWithoutResult((status) -> {
            Image image = imageRepository.findById(imageId).orElseThrow(ImageNotFoundException::new);
            image.addResizedFileEntities(entities);
        });
    }

    @Override
    public String type() {
        return OriginalImageSavedEvent.EVENT_TYPE;
    }

    protected Long toUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    protected BufferedImage getBufferedImage(String path) {
        try {
            return ImageIO.read(new FileInputStream(path));
        } catch (IOException ex) {
            this.logger.error("Cannot read image from path: {}", path);
            throw new RuntimeException("No file was found in the path " + path);
        }

    }

    protected Image getImage(ResizedFileSavingResult result) {
        String resultPath = result.getPath();
        return Image.builder()
                .height(result.getHeight())
                .width(result.getWidth())
                .storageType(ImageStorageType.LOCAL)
                .path(resultPath)
                .fileCreated(toUnixTimestamp())
                .size(FileUtils.sizeOf(new File(resultPath)))
                .type(FilenameUtils.getExtension(resultPath))
                .build();
    }
}
