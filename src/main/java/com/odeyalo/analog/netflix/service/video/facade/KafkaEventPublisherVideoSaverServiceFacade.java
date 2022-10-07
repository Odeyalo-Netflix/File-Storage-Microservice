package com.odeyalo.analog.netflix.service.video.facade;

import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import com.odeyalo.analog.netflix.service.broker.kafka.sender.KafkaMessageSender;
import com.odeyalo.analog.netflix.service.video.VideoSaverService;
import com.odeyalo.analog.netflix.support.events.EventPublisher;
import com.odeyalo.analog.netflix.support.events.OriginalVideoFileSavedEvent;
import com.odeyalo.support.clients.filestorage.dto.VideoUploadedSuccessMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Publish event to specific kafka topic if upload was successful or failed
 */
@Component
public class KafkaEventPublisherVideoSaverServiceFacade implements VideoSaverServiceFacade {
    private final KafkaMessageSender<String, VideoUploadedSuccessMessageDTO> template;
    private final VideoSaverService videoSaverService;
    private final Logger logger = LoggerFactory.getLogger(KafkaEventPublisherVideoSaverServiceFacade.class);
    private final EventPublisher eventPublisher;
    private final VideoRepository repository;

    @Autowired
    public KafkaEventPublisherVideoSaverServiceFacade(KafkaMessageSender<String, VideoUploadedSuccessMessageDTO> template,
                                                      VideoSaverService videoSaverService, EventPublisher eventPublisher,
                                                      VideoRepository repository) {
        this.template = template;
        this.videoSaverService = videoSaverService;
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @Override
    public String uploadVideo(MultipartFile file) throws VideoUploadException {
        try {
            String videoId = this.videoSaverService.uploadVideo(file);
            Video video = repository.findById(videoId).get();
            this.eventPublisher.publishEvent(OriginalVideoFileSavedEvent.EVENT_NAME, new OriginalVideoFileSavedEvent(videoId, video.getPath()));
            this.template.send("VIDEO_UPLOADED_SUCCESS", new VideoUploadedSuccessMessageDTO(generateEventId(), video.getId(), videoId));
            return videoId;
        } catch (VideoUploadException | VideoAlreadyExistException e) {
            this.logger.error("Failed to upload video", e);
            this.template.send("VIDEO_UPLOAD_FAILED", new VideoUploadedSuccessMessageDTO("", "", ""));
        }
        throw new VideoUploadException("Failed to upload video to server");
    }


    private String generateEventId() {
        return UUID.randomUUID().toString();
    }
}
