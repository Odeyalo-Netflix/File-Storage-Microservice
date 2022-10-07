package com.odeyalo.analog.netflix.service.broker.kafka.support;

public class KafkaStateHolder {
    private static KafkaState state;

    public KafkaStateHolder(KafkaState state) {
        KafkaStateHolder.state = state;
    }

    public static KafkaState getState() {
        return state;
    }

    public static void setState(KafkaState state) {
        KafkaStateHolder.state = state;
    }
}
