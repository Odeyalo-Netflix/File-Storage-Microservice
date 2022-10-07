package com.odeyalo.analog.netflix.service.broker.kafka.support;

import java.time.LocalDateTime;

/**
 * Handler to handle message if broker not available now
 */
public interface KafkaBrokerNotAvailableHandler {


    /**
     * @param topic       - topic that was failed
     * @param message     - original message
     * @param state       - state of kafka broker when broker was not available
     * @param currentTime - time when broker was not available
     */
    void handle(String topic, Object message, KafkaState state, LocalDateTime currentTime);

}
