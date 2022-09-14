package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.size.ImageResizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class RemoteImageManager extends AbstractImageManager {
    private final ImageRepository imageRepository;
    private final ImageSaverService imageSaverService;
    private final ImageResizer imageResizer;

    public RemoteImageManager(ImageRepository imageRepository, @Qualifier("thumbsnapRemoteImageSaverService") ImageSaverService imageSaverService, ImageResizer imageResizer) {
        this.imageRepository = imageRepository;
        this.imageSaverService = imageSaverService;
        this.imageResizer = imageResizer;
    }


    @Override
    public String saveImage(MultipartFile file) throws IOException, UploadException {
        Image image = this.imageSaverService.saveImage(file);
        this.logger.info("Saved image: {}", image);
        return image.getId();
    }

    @Override
    public Resource getImageById(String imageId) throws IOException, ImageNotReadableException {
        Image image = this.imageRepository.findById(imageId).orElseThrow(() -> {
            throw new ImageNotFoundException("Image with id: " + imageId + " was not found");
        });
        if (image.getStorageType() == ImageStorageType.REMOTE) {
            return new UrlResource(image.getPath());
        }
        this.logger.info("Reading image from path: {}", image.getPath());
        BufferedImage bufferedImage = imageToBufferedImage(image);
        return bufferedImageToResource(bufferedImage, image);
    }

    @Override
    public Resource changeImageSize(String imageId, Integer height, Integer width) throws IOException, ImageNotReadableException {
        Image image = this.imageRepository.findById(imageId).orElseThrow(() -> {
            throw new ImageNotFoundException("Image not exist");
        });
        BufferedImage bufferedImage = this.imageToBufferedImage(image);
        BufferedImage resizedImage = this.imageResizer.resize(bufferedImage, width, height);
        return bufferedImageToResource(resizedImage, image);
    }


    @Override
    public void compressImage(Image image) {
    }

    @Override
    public void deleteImage(String imageId) {

    }
}
