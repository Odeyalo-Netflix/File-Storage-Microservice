package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface VideoSaverService {
    /**
     *
     * @param video - video to save
     * @return - video id
     * @throws VideoUploadException - if some problems was occurred while video upload
     * @throws VideoAlreadyExistException - if video exist
     */
    String uploadVideo(MultipartFile video) throws VideoUploadException, VideoAlreadyExistException;

}
