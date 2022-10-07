package com.odeyalo.analog.netflix.service.broker.kafka.sender;

import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaBrokerNotAvailableHandler;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaState;
import com.odeyalo.analog.netflix.service.broker.kafka.support.KafkaStateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;

/**
 * Default implementation of KafkaMessageSender. Check broker status before message sending.
 * If status is FALSE no message will be sent and current message handling will be delegated to KafkaBrokerNotAvailableExceptionHandler.
 *
 * @param <K>
 * @param <V>
 * @see KafkaBrokerNotAvailableHandler
 */
public class DefaultKafkaMessageSender<K, V> implements KafkaMessageSender<K, V> {
    private final KafkaTemplate<K, V> kafkaTemplate;
    private final KafkaBrokerNotAvailableHandler exceptionHandler;
    private final Logger logger = LoggerFactory.getLogger(DefaultKafkaMessageSender.class);


    public DefaultKafkaMessageSender(KafkaTemplate<K, V> kafkaTemplate, KafkaBrokerNotAvailableHandler exceptionHandler) {
        this.kafkaTemplate = kafkaTemplate;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void send(String topic, V value) {
        KafkaState state = KafkaStateHolder.getState();
        if (!state.status()) {
            this.logger.error("Kafka broker is not available. Delegate exception handling to: {}", exceptionHandler);
            this.exceptionHandler.handle(topic, value, state, LocalDateTime.now());
            return;
        }
        sendAndLog(topic, null, value);
    }

    @Override
    public void send(String topic, K key, V value) {
        KafkaState state = KafkaStateHolder.getState();
        if (!state.status()) {
            this.logger.error("Kafka broker is not available. Delegate exception handling to: {}", exceptionHandler);
            this.exceptionHandler.handle(topic, value, state, LocalDateTime.now());
            return;
        }
        sendAndLog(topic, key, value);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendAndReturn(String topic, V value) {
        KafkaState state = KafkaStateHolder.getState();
        if (!state.status()) {
            this.logger.error("Kafka broker is not available. Delegate exception handling to: {}", exceptionHandler);
            this.exceptionHandler.handle(topic, value, state, LocalDateTime.now());
            return null;
        }
        return this.kafkaTemplate.send(topic, value);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendAndReturn(String topic, K key, V value) {
        return this.kafkaTemplate.send(topic, key, value);
    }


    private void sendAndLog(String topic, K key, V value) {
        this.kafkaTemplate.send(topic, key, value).addCallback((success) -> {
            this.logger.info("Successful sent message: {}", success.getProducerRecord());
        }, (error) -> {
            this.logger.error("Failed to send kafka message", error);
        });
    }

}
