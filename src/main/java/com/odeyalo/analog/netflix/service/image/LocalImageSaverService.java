package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.storage.LocalFileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LocalImageSaverService implements ImageSaverService {
    private final LocalFileUploader fileUploader;
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(LocalImageSaverService.class);

    @Autowired
    public LocalImageSaverService(LocalFileUploader fileUploader, ImageRepository imageRepository) {
        this.fileUploader = fileUploader;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(MultipartFile file) throws UploadException {
        try {
            String path = this.fileUploader.save(file);
            Image image = this.imageRepository.save(Image.builder().type(ImageStorageType.LOCAL).path(path).build());
            this.logger.info("Saved image: {}", image);
            return image;
        } catch (IOException ex) {
            this.logger.error("File upload: {} has been failed.", file.getOriginalFilename(), ex);
            throw new UploadException(String.format("Cannot upload file %s, please try again later", file.getName()));
        }
    }
}