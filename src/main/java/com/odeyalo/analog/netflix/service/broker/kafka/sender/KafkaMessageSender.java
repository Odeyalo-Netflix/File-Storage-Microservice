package com.odeyalo.analog.netflix.service.broker.kafka.sender;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Supoort class to send kafka messages.
 *
 * @param <K> - key
 * @param <V> - value
 */
public interface KafkaMessageSender<K, V> {

    void send(String topic, V value);

    void send(String topic, K key, V value);


    ListenableFuture<SendResult<K, V>> sendAndReturn(String topic, V value);

    ListenableFuture<SendResult<K, V>> sendAndReturn(String topic, K key, V value);

}
