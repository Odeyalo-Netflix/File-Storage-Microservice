package com.odeyalo.analog.netflix.service;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.image.ImageSaverService;
import com.odeyalo.analog.netflix.service.size.ImageResizer;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RemoteImageManager implements ImageManager {
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(RemoteImageManager.class);
    private final ImageSaverService imageSaverService;
    private final ImageResizer imageResizer;

    public RemoteImageManager(ImageRepository imageRepository, ImageSaverService imageSaverService, ImageResizer imageResizer) {
        this.imageRepository = imageRepository;
        this.imageSaverService = imageSaverService;
        this.imageResizer = imageResizer;
    }


    @Override
    public void saveUserImage(MultipartFile file, String userId) throws IOException, UploadException {
        this.logger.info("Saving image with name: {}. User id: {}", file.getOriginalFilename(), userId);
        this.imageSaverService.saveUserImage(file, userId);
        this.logger.info("Saved image with name: {}", file.getOriginalFilename());
    }

    @Override
    public String saveVideoImage(MultipartFile file, String remoteImageId) throws UploadException, IOException {
        return this.imageSaverService.saveVideoImage(file, remoteImageId);
    }

    @Override
    public Resource getImageById(String imageId) throws IOException, ImageNotReadableException {
        Image image = this.imageRepository.findById(imageId).orElseThrow(() -> {
            throw new ImageNotFoundException("Image with id: " + imageId + " was not found");
        });
        if (image.getType() == ImageStorageType.REMOTE) {
            return new UrlResource(image.getPath());
        }
        this.logger.info("Reading image from path: {}", image.getPath());
        BufferedImage bufferedImage = getBufferedImage(image);
        return bufferedImageToResource(bufferedImage, image);
    }

    @Override
    public List<String> getImagesByUserId(String userId) {
        List<Image> images = this.imageRepository.findAllByUserId(userId);
        return images.stream().map(Image::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Resource changeImageSize(String imageId, Integer height, Integer width) throws IOException, ImageNotReadableException {
        Image image = this.imageRepository.findById(imageId).orElseThrow(() -> {
            throw new ImageNotFoundException("Image not exist");
        });
        BufferedImage bufferedImage = this.getBufferedImage(image);
        BufferedImage resizedImage = this.imageResizer.resize(bufferedImage, width, height);
        return bufferedImageToResource(resizedImage, image);
    }

    @Override
    public Resource getImageByRemoteImageId(String remoteImageId) throws IOException, ImageNotReadableException {
        Image image = this.imageRepository.findImageByRemoteImageId(remoteImageId).orElseThrow(ImageNotFoundException::new);
        if (image.getType() == ImageStorageType.REMOTE) {
            return new UrlResource(image.getPath());
        }
        this.logger.info("Reading image from path: {}", image.getPath());
        BufferedImage bufferedImage = getBufferedImage(image);
        return bufferedImageToResource(bufferedImage, image);
    }

    @Override
    public void compressImage(Image image) {
    }

    protected Resource bufferedImageToResource(BufferedImage bufferedImage, Image image) throws IOException {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        String extension = FilenameUtils.getExtension(image.getPath());
        ImageIO.write(bufferedImage, extension, array);
        byte[] bytes = array.toByteArray();
        return new ByteArrayResource(bytes);
    }

    protected BufferedImage getBufferedImage(Image image) throws ImageNotReadableException {
        String path = image.getPath();
        File file = new File(path);
        try {
            return ImageIO.read(file);
        } catch (IOException exception) {
            this.logger.trace("Stacktrace: ", exception);
            throw new ImageNotReadableException("Image cannot be read.");
        }
    }
}
