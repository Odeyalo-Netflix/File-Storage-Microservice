package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.dto.Base64ImageDTO;
import com.odeyalo.support.clients.filestorage.dto.VideoUploadedSuccessMessageDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;


@Configuration
public class ApacheKafkaConsumerConfiguration {
    private static final String APACHE_KAFKA_MESSAGE_BROKER_GROUP_ID_CONFIG = "1";

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Base64ImageDTO> base64ImageDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Base64ImageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(imageResizeConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Base64ImageDTO> imageResizeConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(Base64ImageDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> bytesConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(imageCompressConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, byte[]> imageCompressConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ByteArrayDeserializer());
    }

    @Bean
    public ConsumerFactory<String, VideoUploadedSuccessMessageDTO> videoUploadProcessResponseDTOConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(VideoUploadedSuccessMessageDTO.class));
    }

    private HashMap<String, Object> consumerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.getConnectionUrl());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, APACHE_KAFKA_MESSAGE_BROKER_GROUP_ID_CONFIG);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, 10000);
        return config;
    }
}
