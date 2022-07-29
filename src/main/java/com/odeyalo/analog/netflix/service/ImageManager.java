package com.odeyalo.analog.netflix.service;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManager {
    /**
     *
     * @param file - file to save
     * @return - unique image id
     * @throws UploadException - if image uploading failed
     * @throws IOException - if cannot process image and save it
     */
    String saveImage(MultipartFile file) throws UploadException, IOException;

    Resource getImageById(String imageId) throws ImageNotFoundException, IOException, ImageNotReadableException;

    Resource changeImageSize(String imageId, Integer height, Integer width) throws IOException, ImageNotReadableException;

    void compressImage(Image image);
}
