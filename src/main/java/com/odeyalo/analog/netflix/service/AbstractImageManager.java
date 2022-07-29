package com.odeyalo.analog.netflix.service;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Contains utility methods for image managers
 */
public abstract class AbstractImageManager implements ImageManager {
    protected final Logger logger = LoggerFactory.getLogger(AbstractImageManager.class);


    protected Resource imageToResource(Image image) throws MalformedURLException {
        String path = image.getPath();
        return new FileUrlResource(path);
    }

    protected Resource bufferedImageToResource(BufferedImage bufferedImage, Image image) throws IOException {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        String extension = FilenameUtils.getExtension(image.getPath());
        ImageIO.write(bufferedImage, extension, array);
        byte[] bytes = array.toByteArray();
        return new ByteArrayResource(bytes);
    }


    /**
     *
     * @param image - image that will be converted to buffered image
     * @return - BufferedImage from image path
     * @throws ImageNotReadableException - if cannot read or convert image to BufferedImage
     */
    protected BufferedImage imageToBufferedImage(Image image) throws ImageNotReadableException {
        try {
            String path = image.getPath();
            File file = new File(path);
            return ImageIO.read(file);
        } catch (IOException exception) {
            this.logger.trace("Stacktrace: ", exception);
            throw new ImageNotReadableException("Image cannot be read.");
        }
    }
}
