package com.odeyalo.analog.netflix.config;

import com.odeyalo.analog.netflix.service.files.VideoResolution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VideoResolutionConfiguration {

    @Bean
    public List<VideoResolution> resolutions() {
        return List.of(
                new VideoResolution(200, 200),
                new VideoResolution(400, 400),
                new VideoResolution(600, 600));
    }
}
