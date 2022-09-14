package com.odeyalo.analog.netflix.service.video.facade;

import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.service.video.VideoSaverService;
import com.odeyalo.support.clients.filestorage.dto.VideoUploadedSuccessMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

/**
 * Publish event to specific kafka topic if upload was successful or failed
 */
@Component
public class KafkaEventPublisherVideoSaverServiceFacade implements VideoSaverServiceFacade {
    private final KafkaTemplate<String, VideoUploadedSuccessMessageDTO> template;
    private final VideoSaverService videoSaverService;
    private final Logger logger = LoggerFactory.getLogger(KafkaEventPublisherVideoSaverServiceFacade.class);

    @Autowired
    public KafkaEventPublisherVideoSaverServiceFacade(KafkaTemplate<String, VideoUploadedSuccessMessageDTO> template, VideoSaverService videoSaverService) {
        this.template = template;
        this.videoSaverService = videoSaverService;
    }

    @Override
    public String uploadVideo(MultipartFile file) throws VideoUploadException {
        try {
            String fileId = this.videoSaverService.uploadVideo(file);
            ListenableFuture<SendResult<String, VideoUploadedSuccessMessageDTO>> message = this.template.send("VIDEO_UPLOADED_SUCCESS",
                    new VideoUploadedSuccessMessageDTO("EVENT_1", "VIDEO_ID_1", fileId));
            this.logger.info("Successful uploaded video, kafka message dto: {}", message.get().toString());
            return fileId;
        } catch (VideoUploadException | VideoAlreadyExistException e) {
            this.logger.error("Failed to upload video", e);
            this.template.send("VIDEO_UPLOAD_FAILED", new VideoUploadedSuccessMessageDTO("", "", ""));
        } catch (InterruptedException | ExecutionException e) {
            this.logger.error("Failed to get message from kafka", e);
        }
        throw new VideoUploadException("Failed to upload video to server");
    }
}
