package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import com.odeyalo.analog.netflix.service.storage.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LocalVideoUploadService implements VideoUploadService {
    private final VideoRepository videoRepository;
    private final FileStorage storage;
    private final Logger logger = LoggerFactory.getLogger(LocalVideoUploadService.class);

    @Autowired
    public LocalVideoUploadService(VideoRepository videoRepository, @Qualifier("localFileStorage") FileStorage storage) {
        this.videoRepository = videoRepository;
        this.storage = storage;
    }

    @Override
    public void uploadVideo(MultipartFile videoFile, String videoId) throws VideoUploadException, VideoAlreadyExistException {
        boolean isExist = this.videoRepository.findVideoByVideoId(videoId).isPresent();
        if (isExist) {
            throw new VideoAlreadyExistException("Video with id: " + videoId + " already exist");
        }
        try {
            String path = this.storage.save(videoFile);
            Video video = Video.builder().videoId(videoId).path(path).build();
            this.videoRepository.save(video);
            this.logger.info("Successful saved video: {}", video);
        } catch (IOException | UploadException exception) {
            this.logger.error("Video upload failed. {}, stacktrace: {}", exception.getMessage(), exception.getStackTrace());
            throw new VideoUploadException("We can't process this video");
        }
    }
}
