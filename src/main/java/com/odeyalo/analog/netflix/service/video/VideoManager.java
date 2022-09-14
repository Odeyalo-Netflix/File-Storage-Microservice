package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface VideoManager {
    String saveVideo(MultipartFile file) throws VideoUploadException;

    void deleteVideo(String id);
}
