package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.service.broker.kafka.support.DefaultKafkaAdminClient;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaAdminClient;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaState;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaStateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfiguration {
    public static final String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = "localhost:9092";
    private final Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class);

    @Bean
    public KafkaAdminClient kafkaAdminClient() {
        DefaultKafkaAdminClient defaultKafkaAdminClient = new DefaultKafkaAdminClient(APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        boolean connectionStatus = defaultKafkaAdminClient.verifyConnection();
        KafkaState state = new KafkaState(connectionStatus);
        KafkaStateHolder.setState(state);
        this.logger.info("Current state: {}", state);
        return defaultKafkaAdminClient;
    }
}
