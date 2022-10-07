package com.odeyalo.analog.netflix.support.events;

import com.odeyalo.analog.netflix.support.events.handlers.EventHandler;

import java.util.List;

public interface EventHandlerRegistry {

    void addHandler(String type, EventHandler eventHandler);

    List<EventHandler> getEventHandlers(String type);

}
