package com.odeyalo.analog.netflix.service.broker.kafka.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * KafkaBrokerNotAvailableHandler implementation that logs message that was failed to send
 */
@Component
public class LoggingKafkaBrokerNotAvailableHandler implements KafkaBrokerNotAvailableHandler {
    private final Logger logger = LoggerFactory.getLogger(LoggingKafkaBrokerNotAvailableHandler.class);

    @Override
    public void handle(String topic, Object message, KafkaState state, LocalDateTime currentTime) {
        this.logger.error("Failed to send message: {} to kafka topic: {} in {} with kafka state: {}", message, topic, currentTime, state);
    }
}
