package com.odeyalo.analog.netflix.service;

import com.odeyalo.analog.netflix.service.size.image.compressor.DefaultImageSizeCompressorResolver;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

class DefaultImageSizeCompressorResolverTest {
    DefaultImageSizeCompressorResolver resolver = new DefaultImageSizeCompressorResolver(null);

    @Test
    void changeQuality() throws IOException {
        FileInputStream stream = new FileInputStream("C:\\Users\\thepr_2iz2cnv\\IdeaProjects\\FILE-STORAGE-MICROSERVICE\\compress.jpg");
        String path = this.resolver.compress(stream);
        System.out.println(path);
    }
}
