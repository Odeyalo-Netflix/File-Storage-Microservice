package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageSaverService {

    void saveUserImage(MultipartFile file, String userId) throws UploadException, IOException;

    String saveVideoImage(MultipartFile file, String remoteImageId) throws UploadException, IOException;
}
