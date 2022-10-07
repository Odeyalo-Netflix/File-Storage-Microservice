package com.odeyalo.analog.netflix.service.video;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JFFmpegVideoFileResolutionChangerTest {
    private final JFFmpegVideoFileResolutionChanger changer = new JFFmpegVideoFileResolutionChanger(new FFmpeg(), new FFprobe());

    JFFmpegVideoFileResolutionChangerTest() throws IOException {
    }

    @Test
    void changeVideoResolution() throws Exception {
        long start = System.currentTimeMillis();
        changer.changeVideoResolution("C:\\Users\\thepr_2iz2cnv\\Downloads\\input.mp4",
                "D:\\output.mp4", "mp4", true,
                854, 480);
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) / 1000);

    }
}
