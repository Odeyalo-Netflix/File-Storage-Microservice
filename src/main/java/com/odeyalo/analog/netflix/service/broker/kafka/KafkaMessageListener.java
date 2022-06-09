package com.odeyalo.analog.netflix.service.broker.kafka;

import java.io.IOException;

public interface KafkaMessageListener<T> {

    void receiveMessage(T message) throws IOException;

}
