package com.odeyalo.analog.netflix.support.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OriginalImageSavedEvent extends Event {
    private String imageId;
    private String originalImagePath;

    public static final String EVENT_TYPE = "ORIGINAL_IMAGE_SAVED_EVENT";
}
