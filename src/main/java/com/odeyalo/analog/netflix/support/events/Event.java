package com.odeyalo.analog.netflix.support.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Data
@AllArgsConstructor
public abstract class Event {
    protected String eventId;

    public Event() {
        if (eventId == null) {
            eventId = UUID.randomUUID().toString();
        }
    }
}
