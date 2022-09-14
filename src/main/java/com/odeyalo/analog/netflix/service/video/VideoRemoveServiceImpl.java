package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Implementation that deletes file and entity
 */
@Service
public class VideoRemoveServiceImpl implements VideoRemoveService {
    private final VideoRepository videoRepository;
    private final Logger logger = LoggerFactory.getLogger(VideoRemoveServiceImpl.class);
    @Autowired
    public VideoRemoveServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public void delete(String videoId) {
        Optional<Video> optional = this.videoRepository.findVideoById(videoId);
        if (optional.isPresent()) {
            try {
                Video video = optional.get();
                String path = video.getPath();
                Files.deleteIfExists(Paths.get(path));
                this.videoRepository.delete(video);
            } catch (IOException e) {
                this.logger.error("Cannot delete video: ", e);
            }
        }
    }
}
