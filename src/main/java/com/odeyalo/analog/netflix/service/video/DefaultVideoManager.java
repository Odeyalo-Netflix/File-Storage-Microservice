package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.service.video.facade.VideoSaverServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DefaultVideoManager implements VideoManager {
    private final VideoSaverServiceFacade facade;
    private final VideoRemoveService removeService;

    @Autowired
    public DefaultVideoManager(@Qualifier("kafkaEventPublisherVideoSaverServiceFacade") VideoSaverServiceFacade facade, VideoRemoveService removeService) {
        this.facade = facade;
        this.removeService = removeService;
    }

    @Override
    public String saveVideo(MultipartFile file) throws VideoUploadException {
        return this.facade.uploadVideo(file);
    }

    @Override
    public void deleteVideo(String id) {
        this.removeService.delete(id);
    }
}
