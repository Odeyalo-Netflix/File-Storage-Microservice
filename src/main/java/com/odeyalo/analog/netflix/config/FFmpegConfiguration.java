package com.odeyalo.analog.netflix.config;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("dev")
public class FFmpegConfiguration {

    @Bean
    public FFmpeg ffmpeg() throws IOException {
        return new FFmpeg();
    }

    @Bean
    public FFprobe ffprobe() throws IOException {
        return new FFprobe();
    }
}
