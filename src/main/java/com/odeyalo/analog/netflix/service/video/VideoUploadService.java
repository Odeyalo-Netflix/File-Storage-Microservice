package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface VideoUploadService {

    void uploadVideo(MultipartFile video, String videoId) throws VideoUploadException, VideoAlreadyExistException;

}
