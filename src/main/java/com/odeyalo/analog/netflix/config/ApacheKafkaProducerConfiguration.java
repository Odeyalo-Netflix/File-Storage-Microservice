package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.dto.ImageResizeDTO;
import com.odeyalo.analog.netflix.service.broker.kafka.sender.DefaultKafkaMessageSender;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaBrokerNotAvailableHandler;
import com.odeyalo.analog.netflix.service.broker.kafka.support.LoggingKafkaBrokerNotAvailableHandler;
import com.odeyalo.support.clients.filestorage.dto.VideoUploadedSuccessMessageDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;


@Configuration
public class ApacheKafkaProducerConfiguration {

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
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaBrokerNotAvailableHandler kafkaBrokerNotAvailableHandler() {
        return new LoggingKafkaBrokerNotAvailableHandler();
    }

    @Bean
    public DefaultKafkaMessageSender<String, VideoUploadedSuccessMessageDTO> videoUploadedSuccessMessageDTODefaultKafkaMessageSender(
            KafkaTemplate<String, VideoUploadedSuccessMessageDTO> template, KafkaBrokerNotAvailableHandler kafkaBrokerNotAvailableHandler) {
        return new DefaultKafkaMessageSender<>(template, kafkaBrokerNotAvailableHandler);
    }

    private HashMap<String, Object> producerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.getConnectionUrl());
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 10000);
        return config;
    }
}
