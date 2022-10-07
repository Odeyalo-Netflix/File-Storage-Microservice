package com.odeyalo.analog.netflix.service.video;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Change video resolution using JFFmpeg library
 */
@Component
public class JFFmpegVideoFileResolutionChanger implements VideoFileResolutionChanger {
    private final Logger logger = LoggerFactory.getLogger(JFFmpegVideoFileResolutionChanger.class);
    private final FFmpeg ffmpeg;
    private final FFprobe ffprobe;

    @Autowired
    public JFFmpegVideoFileResolutionChanger(FFmpeg ffmpeg, FFprobe ffprobe) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
    }

    @Override
    public void changeVideoResolution(String originalFilePath,
                                      String outputPath, String outputFormat,
                                      String videoCodec, boolean overrideExist,
                                      Integer height, Integer width){
        FFmpegBuilder build = new FFmpegBuilder()
                .overrideOutputFiles(overrideExist)
                .addInput(originalFilePath)
                .addOutput(outputPath)
                .setVideoFilter("scale=" + height + ":" + width)
                .setFormat(outputFormat)
                .setVideoCodec(videoCodec)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(build).run();
    }
}
