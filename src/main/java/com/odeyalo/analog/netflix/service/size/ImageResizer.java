package com.odeyalo.analog.netflix.service.size;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageResizer {
    BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight) throws IOException;
}
