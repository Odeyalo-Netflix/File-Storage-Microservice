package com.odeyalo.analog.netflix.support.events;

import com.odeyalo.analog.netflix.support.events.handlers.EventHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class EventHandlerContainer implements EventHandlerRegistry {
    private final Map<String, List<EventHandler>> handlers = new ConcurrentHashMap<>();

    @Override
    public void addHandler(String type, EventHandler eventHandler) {
        List<EventHandler> eventHandlers = this.handlers.computeIfAbsent(type, (x) -> new ArrayList<>());
        eventHandlers.add(eventHandler);
        this.handlers.put(type, eventHandlers);
        log.info("Saved: {} with type {}", eventHandler, type);
    }

    @Override
    public List<EventHandler> getEventHandlers(String type) {
        return handlers.get(type);
    }
}
