package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import com.odeyalo.analog.netflix.service.storage.FileUploader;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LocalVideoSaverService implements VideoSaverService {
    private final VideoRepository videoRepository;
    private final FileUploader storage;
    private final Logger logger = LoggerFactory.getLogger(LocalVideoSaverService.class);

    @Autowired
    public LocalVideoSaverService(VideoRepository videoRepository, @Qualifier("localFileUploader") FileUploader storage) {
        this.videoRepository = videoRepository;
        this.storage = storage;
    }

    @Override
    public String uploadVideo(MultipartFile videoFile) throws VideoUploadException {
        try {
            String path = this.storage.save(videoFile);
            Video video = Video
                    .builder()
                    .fileCreated(toUnixTimestamp())
                    .size(videoFile.getSize())
                    .type(FilenameUtils.getExtension(path))
                    .path(path)
                    .build();
            Video savedVideo = this.videoRepository.save(video);
            String videoId = savedVideo.getId();
            this.logger.info("Saved video from file: {}, video id: {}", videoFile, videoId);
            return videoId;
        } catch (IOException | UploadException exception) {
            this.logger.error("Video upload failed. {}, stacktrace: {}", exception.getMessage(), exception.getStackTrace());
            throw new VideoUploadException("We can't process this video");
        }
    }
    protected Long toUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

}
