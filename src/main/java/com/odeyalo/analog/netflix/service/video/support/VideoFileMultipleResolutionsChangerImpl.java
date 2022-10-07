package com.odeyalo.analog.netflix.service.video.support;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;
import com.odeyalo.analog.netflix.service.files.VideoResolution;
import com.odeyalo.analog.netflix.service.storage.FileUploader;
import com.odeyalo.analog.netflix.service.storage.FileUploaderFactory;
import com.odeyalo.analog.netflix.service.video.VideoFileResolutionChanger;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoFileMultipleResolutionsChangerImpl implements VideoFileMultipleResolutionsChanger {
    private final Logger logger = LoggerFactory.getLogger(VideoFileMultipleResolutionsChangerImpl.class);
    private final VideoFileResolutionChanger resolutionChanger;
    private final FileUploaderFactory fileUploaderFactory;
    private final String tempPath;

    @Autowired
    public VideoFileMultipleResolutionsChangerImpl(VideoFileResolutionChanger resolutionChanger,
                                                   FileUploaderFactory fileUploaderFactory,
                                                   @Value("${app.files.saving.path.temp}") String tempPath) {
        this.resolutionChanger = resolutionChanger;
        this.fileUploaderFactory = fileUploaderFactory;
        this.tempPath = tempPath;
    }

    @SneakyThrows
    @Override
    public List<ResizedFileSavingResult> changeVideoResolution(String pathToVideo, List<VideoResolution> videoResolutions) {
        List<ResizedFileSavingResult> results = new ArrayList<>();
        FileUploader fileUploader = fileUploaderFactory.getFileUploader();
        for (VideoResolution resolution : videoResolutions) {
            String path = changeResolution(pathToVideo, resolution.getHeight(), resolution.getWidth());
            if (path != null) {
                String save = fileUploader.save(new File(path));
                results.add(new ResizedFileSavingResult(resolution.getWidth(), resolution.getHeight(), path, true));
                this.logger.info("Saved: {}", save);
            } else {
                results.add(new ResizedFileSavingResult(resolution.getWidth(), resolution.getHeight(), null, false));
            }
        }
        return results;
    }


    private String changeResolution(String pathToVideo, Integer height, Integer width) {
        try {
            String output = tempPath + UUID.randomUUID().toString() +  "." + FilenameUtils.getExtension(pathToVideo);
            this.resolutionChanger.changeVideoResolution(pathToVideo, output, FilenameUtils.getExtension(pathToVideo), false, height, width);
            return output;
        } catch (Exception ex) {
            this.logger.error("Error saving file", ex);
        }
        return null;
    }
}
