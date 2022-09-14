package com.odeyalo.analog.netflix.service.broker.kafka;

import com.odeyalo.support.clients.filestorage.dto.VideoUploadProcessMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Listens to event that fires when video uploading or saving was failed and revert file saving
 */
@Component
public class VideoUploadProcessFailedMessageKafkaMessageListener implements KafkaMessageListener<VideoUploadProcessMessage> {

    @Override
    public void receiveMessage(VideoUploadProcessMessage message) throws IOException {
    }
}
