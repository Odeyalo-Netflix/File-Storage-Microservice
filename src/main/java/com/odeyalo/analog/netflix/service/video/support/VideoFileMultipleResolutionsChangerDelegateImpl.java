package com.odeyalo.analog.netflix.service.video.support;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import com.odeyalo.analog.netflix.service.files.VideoResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoFileMultipleResolutionsChangerDelegateImpl implements VideoFileMultipleResolutionsChangerDelegate {
    private final List<VideoResolution> resolutions;
    private final VideoFileMultipleResolutionsChanger changer;

    @Autowired
    public VideoFileMultipleResolutionsChangerDelegateImpl(List<VideoResolution> resolutions, VideoFileMultipleResolutionsChanger changer) {
        this.resolutions = resolutions;
        this.changer = changer;
    }

    @Override
    public List<ResizedFileSavingResult> changeResolution(String pathToVideo) {
        return changer.changeVideoResolution(pathToVideo, resolutions);
    }
}
