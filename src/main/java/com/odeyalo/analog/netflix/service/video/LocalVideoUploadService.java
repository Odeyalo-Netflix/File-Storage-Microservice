package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {
    private final VideoRepository videoRepository;
    @Override
    public void uploadVideo(MultipartFile video, String videoId) {

    }
}
