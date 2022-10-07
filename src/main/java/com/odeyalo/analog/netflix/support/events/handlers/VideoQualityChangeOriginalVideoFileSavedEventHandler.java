package com.odeyalo.analog.netflix.support.events.handlers;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import com.odeyalo.analog.netflix.service.video.support.VideoFileMultipleResolutionsChangerDelegate;
import com.odeyalo.analog.netflix.support.events.Event;
import com.odeyalo.analog.netflix.support.events.OriginalVideoFileSavedEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VideoQualityChangeOriginalVideoFileSavedEventHandler implements OriginalVideoFileSavedEventHandler {
    private final VideoFileMultipleResolutionsChangerDelegate changerDelegate;
    private final TransactionTemplate template;
    private final VideoRepository videoRepository;

    @Autowired
    public VideoQualityChangeOriginalVideoFileSavedEventHandler(VideoFileMultipleResolutionsChangerDelegate changerDelegate, TransactionTemplate template, VideoRepository videoRepository) {
        this.changerDelegate = changerDelegate;
        this.template = template;
        this.videoRepository = videoRepository;
    }

    @Override
    public void handleEvent(Event event) {
        if (!isCorrectEvent(event)) {
            logger.info("Wrong event was received: {}, expected: {}", event.getClass().getName(), OriginalVideoFileSavedEvent.class.getName());
            return;
        }

        OriginalVideoFileSavedEvent fileSavedEvent = (OriginalVideoFileSavedEvent) event;
        String path = fileSavedEvent.getPath();
        String id = fileSavedEvent.getId();

        List<Video> videos = changerDelegate.changeResolution(path)
                .stream()
                .map(this::getVideo)
                .collect(Collectors.toList());
        template.executeWithoutResult((status) -> {
            Video video = videoRepository.findById(id).orElseThrow(VideoNotFoundException::new);
            videoRepository.saveAll(videos);
            video.addResizedVideos(videos);
        });
    }

    protected Video getVideo(ResizedFileSavingResult result) {
        String resultPath = result.getPath();
        return Video.builder()
                .height(result.getHeight())
                .width(result.getWidth())
                .path(resultPath)
                .fileCreated(toUnixTimestamp())
                .size(FileUtils.sizeOf(new File(resultPath)))
                .type(FilenameUtils.getExtension(resultPath))
                .build();
    }

    protected Long toUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
