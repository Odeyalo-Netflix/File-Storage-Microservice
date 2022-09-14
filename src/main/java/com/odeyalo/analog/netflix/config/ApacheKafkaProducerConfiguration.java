package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.dto.ImageResizeDTO;
import com.odeyalo.support.clients.filestorage.dto.VideoUploadedSuccessMessageDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;

@Configuration
public class ApacheKafkaProducerConfiguration {
    private static final String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = "localhost:9092";

    @Bean
    public ProducerFactory<String, ImageResizeDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, ImageResizeDTO> kafkaTemplate(ProducerFactory<String, ImageResizeDTO> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public ProducerFactory<String, VideoUploadedSuccessMessageDTO> videoUploadedSuccessMessageDTOProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, VideoUploadedSuccessMessageDTO> videoUploadedSuccessMessageDTOKafkaTemplate(ProducerFactory<String, VideoUploadedSuccessMessageDTO> factory) {
        KafkaTemplate<String, VideoUploadedSuccessMessageDTO> template = new KafkaTemplate<>(factory);
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template, (r, e) -> {
            System.out.println("Exception" + e);
            return new TopicPartition(r.topic(), r.partition());
        });
        return template;
    }

    private HashMap<String, Object> producerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, "3000");
        return config;
    }


    @Log4j2
    static class CustomProducerListener implements ProducerListener<String, VideoUploadedSuccessMessageDTO> {
        @Override
        public void onError(ProducerRecord<String, VideoUploadedSuccessMessageDTO> producerRecord, RecordMetadata recordMetadata, Exception exception) {
            log.error("Error", exception);
        }
    }
}
