package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.exceptions.UploadException;
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
import java.util.ArrayList;

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
        String path = saveVideoFile(videoFile);
        Video video = buildAndSaveVideo(videoFile, path);
        this.logger.info("Saved video: {}", video);
        return video.getId();
    }

    protected Long toUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }


    private String saveVideoFile(MultipartFile videoFile) throws VideoUploadException {
        try {
            return this.storage.save(videoFile);
        } catch (UploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            this.logger.error("Video upload failed. {}, stacktrace: {}", e.getMessage(), e.getStackTrace());
        }
        throw new VideoUploadException("We can't process this video");
    }

    private Video buildAndSaveVideo(MultipartFile videoFile, String path) {
        Video video = Video
                .builder()
                .fileCreated(toUnixTimestamp())
                .size(videoFile.getSize())
                .type(FilenameUtils.getExtension(path))
                .path(path)
                .resizedVideos(new ArrayList<>())
                .build();
        return this.videoRepository.save(video);
    }
}
