package com.odeyalo.analog.netflix.service.broker.kafka.compressor;

import com.odeyalo.analog.netflix.dto.Base64ImageDTO;
import com.odeyalo.analog.netflix.service.broker.kafka.KafkaMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Base64ImageCompressorKafkaMessageListener implements KafkaMessageListener<Base64ImageDTO> {
    private static final String BASE64_IMAGE_COMPRESSOR_KAFKA_TOPIC = "BASE64_IMAGE_COMPRESSOR_TOPIC";
    private final Logger logger = LoggerFactory.getLogger(Base64ImageCompressorKafkaMessageListener.class);

    @Override
    @KafkaListener(topics = BASE64_IMAGE_COMPRESSOR_KAFKA_TOPIC, containerFactory = "base64ImageDTOConcurrentKafkaListenerContainerFactory")
    public void receiveMessage(Base64ImageDTO message) throws IOException {
        this.logger.info("Receive new message: {}", message.getUsername());
    }
}
