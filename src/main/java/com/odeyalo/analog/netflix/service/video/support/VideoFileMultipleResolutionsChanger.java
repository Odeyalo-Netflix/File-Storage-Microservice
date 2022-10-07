package com.odeyalo.analog.netflix.service.video.support;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import com.odeyalo.support.clients.filestorage.dto.VideoResolution;

import java.util.List;

/**
 * Change given video to multiple resolutions
 */
public interface VideoFileMultipleResolutionsChanger {

    /**
     * Convert given video file to multiple files with different resolutions
     * @param pathToVideo - path to original video
     * @param videoResolutions - required resolutions for given video,
     * @return - list of ResizedFileSavingResult with info about path and other info.
     */
    List<ResizedFileSavingResult> changeVideoResolution(String pathToVideo, List<VideoResolution> videoResolutions);
}
