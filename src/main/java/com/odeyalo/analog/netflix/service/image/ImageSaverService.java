package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageSaverService {
    /**
     *
     * @param file - image to save
     * @return - returns saved image object
     * @throws UploadException - if image cannot be uploaded
     */
    Image saveImage(MultipartFile file) throws UploadException, IOException;
}
