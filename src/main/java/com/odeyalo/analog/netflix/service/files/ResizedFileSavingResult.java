package com.odeyalo.analog.netflix.service.files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResizedFileSavingResult {
    private Integer width;
    private Integer height;
    private String path;
    private boolean success;
}
