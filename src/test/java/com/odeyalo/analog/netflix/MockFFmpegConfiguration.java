package com.odeyalo.analog.netflix;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockFFmpegConfiguration {

    @Bean
    public FFmpeg fFmpeg() {
        System.out.println("Mock ffmpeg called");
        return Mockito.mock(FFmpeg.class);
    }

    @Bean
    public FFprobe fFprobe() {
        System.out.println("Mock ffprobe called");
        return Mockito.mock(FFprobe.class);
    }
}
