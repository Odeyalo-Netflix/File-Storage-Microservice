package com.odeyalo.analog.netflix.service.image.size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageResizeData {
    private Integer height;
    private Integer width;
}
