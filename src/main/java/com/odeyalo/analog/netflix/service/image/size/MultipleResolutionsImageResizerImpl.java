package com.odeyalo.analog.netflix.service.image.size;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MultipleResolutionsImageResizerImpl implements MultipleResolutionsImageResizer {
    private final ImageResizer imageResizer;
    private final Logger logger = LoggerFactory.getLogger(MultipleResolutionsImageResizerImpl.class);
    private final String folderPath;


    @Autowired
    public MultipleResolutionsImageResizerImpl(ImageResizer imageResizer,
                                               @Value("${app.image.saving.path.default}") String folderPath) {
        this.imageResizer = imageResizer;
        this.folderPath = folderPath;
    }

    @Override
    public List<ResizedFileSavingResult> resizeImage(BufferedImage image, List<ImageResizeData> data) {
        return resizeImage(folderPath, JPG_EXTENSION, image, data);
    }

    @Override
    public List<ResizedFileSavingResult> resizeImage(String folderPath, String extension, BufferedImage image, List<ImageResizeData> data) {
        List<ResizedFileSavingResult> results = new ArrayList<>();
        for (ImageResizeData resizeData : data) {
            try {
                BufferedImage resizedImage = this.imageResizer.resize(image, resizeData.getWidth(), resizeData.getHeight());
                File file = new File(savingPathBuilder(resizeData, extension));
                boolean result = ImageIO.write(resizedImage, extension, file);
                ResizedFileSavingResult resizedFileSavingResult = new ResizedFileSavingResult(resizeData.getWidth(), resizeData.getHeight(), file.getAbsolutePath(), result);
                results.add(resizedFileSavingResult);
                this.logger.info("Saved image with name: {} and result: {}", file.getAbsolutePath(), result);
            } catch (Exception ex) {
                this.logger.error("Exception during file saving", ex);
            }
        }
        return results;
    }


    String savingPathBuilder(ImageResizeData resizeData, String extension) {
        return new StringBuilder().append(folderPath)
                .append(UUID.randomUUID().toString())
                .append("h")
                .append(resizeData.getHeight())
                .append("w")
                .append(resizeData.getWidth())
                .append(DOT)
                .append(extension).toString();
    }
}
