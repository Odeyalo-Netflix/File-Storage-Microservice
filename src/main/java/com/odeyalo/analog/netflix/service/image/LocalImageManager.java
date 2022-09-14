package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.size.ImageResizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class LocalImageManager extends AbstractImageManager {
    private final ImageRepository imageRepository;
    private final ImageSaverService imageSaverService;
    private final ImageRemoveService imageRemoveService;
    private final ImageResizer imageResizer;


    @Autowired
    public LocalImageManager(ImageRepository imageRepository, @Qualifier("localImageSaverService")
            ImageSaverService imageSaverService, ImageRemoveService imageRemoveService, ImageResizer imageResizer) {
        this.imageRepository = imageRepository;
        this.imageSaverService = imageSaverService;
        this.imageRemoveService = imageRemoveService;
        this.imageResizer = imageResizer;
    }

    @Override
    public String saveImage(MultipartFile file) throws UploadException, IOException {
        Image image = this.imageSaverService.saveImage(file);
        return image.getId();
    }

    @Override
    public Resource getImageById(String imageId) throws ImageNotFoundException, ImageNotReadableException {
        Image image = this.imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(String.format("Image with id: %s not found", imageId)));
        try {
            if (image.getStorageType() == ImageStorageType.LOCAL) {
                return imageToResource(image);
            }
            BufferedImage bufferedImage = imageToBufferedImage(image);
            return bufferedImageToResource(bufferedImage, image);
        } catch (ImageNotReadableException e) {
            this.logger.error("Image: {} cannot be read, stacktrace: {}", image, e);
            throw new ImageNotReadableException("Image cannot be shown");
        } catch (IOException e) {
            this.logger.error("Image: {} cannot be casted to buffered image: {}", image, e);
            throw new ImageNotReadableException("Image cannot be shown");
        }
    }

    @Override
    public Resource changeImageSize(String imageId, Integer height, Integer width) throws IOException, ImageNotReadableException {
        return null;
    }

    @Override
    public void compressImage(Image image) {

    }

    @Override
    public void deleteImage(String imageId) {
        this.imageRemoveService.delete(imageId);
    }
}
