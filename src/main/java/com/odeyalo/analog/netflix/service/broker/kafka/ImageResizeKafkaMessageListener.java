package com.odeyalo.analog.netflix.service.broker.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageResizeKafkaMessageListener implements KafkaMessageListener<String> {
//    private final QualityChangeResolver changeResolver;

//    public ImageResizeKafkaMessageListener(QualityChangeResolver changeResolver) {
//        this.changeResolver = changeResolver;
//    }

    @Override
    @KafkaListener(topics = "IMAGE_RESOLVER", containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(String dto) throws IOException {
        System.out.println(dto);
//        this.changeResolver.changeQuality(dto.getHeight(), dto.getWidth(), dto.getImage().getInputStream());
    }
}
