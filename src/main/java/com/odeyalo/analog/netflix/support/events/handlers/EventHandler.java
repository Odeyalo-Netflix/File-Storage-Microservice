package com.odeyalo.analog.netflix.support.events.handlers;

import com.odeyalo.analog.netflix.support.events.Event;
import com.odeyalo.analog.netflix.support.events.EventHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public interface EventHandler {

    void handleEvent(Event event);

    String type();


    @Autowired
    default void registerMe(EventHandlerRegistry registry) {
        registry.addHandler(type(), this);
    }
}
