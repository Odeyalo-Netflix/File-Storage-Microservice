package com.odeyalo.analog.netflix.support.events;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
public class OriginalVideoFileSavedEvent extends Event {
    public static final String EVENT_NAME = "ORIGINAL_VIDEO_FILE_SAVED_EVENT";
    private final String id;
    private final String path;
}
