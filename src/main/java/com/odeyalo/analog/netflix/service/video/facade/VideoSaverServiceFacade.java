package com.odeyalo.analog.netflix.service.video.facade;

import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface VideoSaverServiceFacade {
    /**
     *
     * @param file - to upload file
     * @return - unique file id
     */
    String uploadVideo(MultipartFile file) throws VideoUploadException;

}
