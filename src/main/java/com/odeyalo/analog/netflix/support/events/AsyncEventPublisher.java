package com.odeyalo.analog.netflix.support.events;

import com.odeyalo.analog.netflix.support.events.handlers.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsyncEventPublisher implements EventPublisher {
    private final EventHandlerRegistry registry;
    private final Logger logger = LoggerFactory.getLogger(AsyncEventPublisher.class);

    @Autowired
    public AsyncEventPublisher(EventHandlerRegistry registry) {
        this.registry = registry;
    }

    @Override
    @Async
    public void publishEvent(String eventType, Event event) {
        List<EventHandler> eventHandlers = this.registry.getEventHandlers(eventType);
        eventHandlers.forEach(handler -> {
            this.logger.info("Published event for: {}", handler);
            handler.handleEvent(event);
        });
    }
}
