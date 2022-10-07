package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.storage.LocalFileUploader;
import com.odeyalo.analog.netflix.support.events.EventPublisher;
import com.odeyalo.analog.netflix.support.events.OriginalImageSavedEvent;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class LocalImageSaverService implements ImageSaverService {
    private final LocalFileUploader fileUploader;
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(LocalImageSaverService.class);
    private final EventPublisher eventPublisher;

    @Autowired
    public LocalImageSaverService(LocalFileUploader fileUploader, ImageRepository imageRepository, EventPublisher eventPublisher) {
        this.fileUploader = fileUploader;
        this.imageRepository = imageRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Image saveImage(MultipartFile file) throws UploadException, IOException {
        String path = null;
        try {
            path = this.fileUploader.save(file);
            Image image = this.imageRepository.save(buildImage(file, path));
            publishEvent(image);
            this.logger.info("Saved image: {}", image);
            return image;
        } catch (Exception ex) {
            this.logger.error("File upload: {} has been failed.", file.getOriginalFilename(), ex);
            Files.deleteIfExists(Paths.get(path));
            throw new UploadException(String.format("Cannot upload file %s, please try again later", file.getName()));
        }
    }

    private void publishEvent(Image image) {
        this.eventPublisher.publishEvent(OriginalImageSavedEvent.EVENT_TYPE, new OriginalImageSavedEvent(image.getId(), image.getPath()));
    }

    private Image buildImage(MultipartFile file, String path) {
        try {
            String extension = FilenameUtils.getExtension(path);
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            return Image.builder()
                    .path(path)
                    .fileCreated(toUnixTimestamp())
                    .storageType(ImageStorageType.LOCAL)
                    .width(bufferedImage.getWidth())
                    .height(bufferedImage.getHeight())
                    .type(extension)
                    .size(file.getSize())
                    .resizedImages(new ArrayList<>())
                    .build();
        } catch (IOException ex) {
            this.logger.error("File reading was failed", ex);
            throw new RuntimeException("Cannot read the file with path: {}" + path, ex);
        }
    }


    protected Long toUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
