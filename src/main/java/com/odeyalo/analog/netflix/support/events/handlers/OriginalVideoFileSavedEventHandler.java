package com.odeyalo.analog.netflix.support.events.handlers;

import com.odeyalo.analog.netflix.support.events.Event;
import com.odeyalo.analog.netflix.support.events.OriginalVideoFileSavedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface OriginalVideoFileSavedEventHandler extends EventHandler {

    Logger logger = LoggerFactory.getLogger(OriginalImageSavedEventHandler.class);

    default boolean isCorrectEvent(Event event) {
        return event instanceof OriginalVideoFileSavedEvent;
    }

    @Override
    default String type() {
        return OriginalVideoFileSavedEvent.EVENT_NAME;
    }
}
