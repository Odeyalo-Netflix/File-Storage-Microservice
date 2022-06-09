package com.odeyalo.analog.netflix.service;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.exceptions.ImageNotFoundException;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageManager {

    void saveUserImage(MultipartFile file, String userId) throws UploadException, IOException;

    void saveVideoImage(MultipartFile file, String remoteImageId) throws UploadException, IOException;

    Resource getImageById(String imageId) throws ImageNotFoundException, IOException, ImageNotReadableException;

    List<String> getImagesByUserId(String userId) throws ImageNotFoundException, ImageNotReadableException;

    Resource changeImageSize(String imageId, Integer height, Integer width) throws IOException, ImageNotReadableException;

    Resource getImageByRemoteImageId(String remoteImageId) throws ImageNotReadableException, IOException;

    void compressImage(Image image);
}
