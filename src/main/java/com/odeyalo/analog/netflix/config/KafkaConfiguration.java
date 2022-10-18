package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.service.broker.kafka.support.DefaultKafkaAdminClient;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaAdminClient;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaState;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaStateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConfiguration {
    private static String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL;
    private final Logger logger = LoggerFactory.getLogger(KafkaConfiguration.class);

    @Autowired
    public KafkaConfiguration(@Value("${app.broker.kafka.connection.url}") String url) {
        APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = url;
        this.logger.info("Kafka connection url: {}", url);
    }

    @Bean
    public KafkaAdminClient kafkaAdminClient() {
        DefaultKafkaAdminClient defaultKafkaAdminClient = new DefaultKafkaAdminClient(APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        boolean connectionStatus = defaultKafkaAdminClient.verifyConnection();
        KafkaState state = new KafkaState(connectionStatus);
        KafkaStateHolder.setState(state);
        this.logger.info("Current state: {}", state);
        return defaultKafkaAdminClient;
    }


    public static String getConnectionUrl() {
        return APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL;
    }
}
