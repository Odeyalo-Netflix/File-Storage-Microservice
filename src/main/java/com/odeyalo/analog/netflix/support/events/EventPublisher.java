package com.odeyalo.analog.netflix.support.events;

public interface EventPublisher {

    void publishEvent(String eventType, Event event);

}
