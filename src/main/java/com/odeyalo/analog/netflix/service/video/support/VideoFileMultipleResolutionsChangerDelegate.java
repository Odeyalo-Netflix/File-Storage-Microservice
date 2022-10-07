package com.odeyalo.analog.netflix.service.video.support;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;

import java.util.List;

public interface VideoFileMultipleResolutionsChangerDelegate {

    List<ResizedFileSavingResult> changeResolution(String pathToVideo);
}
