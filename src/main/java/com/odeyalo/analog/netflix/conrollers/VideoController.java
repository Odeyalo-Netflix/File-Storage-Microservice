package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.annotation.ValidVideoFIle;
import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.service.video.VideoStreamingService;
import com.odeyalo.analog.netflix.service.video.facade.KafkaEventPublisherVideoSaverServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/video")
@Validated
public class VideoController {
    private final KafkaEventPublisherVideoSaverServiceFacade videoSaverService;
    private final VideoStreamingService<ResponseEntity<ResourceRegion>> streamService;

    @Autowired
    public VideoController(KafkaEventPublisherVideoSaverServiceFacade videoSaverService, VideoStreamingService<ResponseEntity<ResourceRegion>> streamService) {
        this.videoSaverService = videoSaverService;
        this.streamService = streamService;
    }

    @GetMapping(value = "/watch", produces = "application/octet-stream")
    public ResponseEntity<ResourceRegion> streamVideo(@RequestParam String videoId, @RequestHeader(name = "range", required = false) String range) throws VideoNotFoundException, IOException {
        return this.streamService.stream(videoId, range);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> saveVideo(@ValidVideoFIle @RequestPart(name = "video") MultipartFile file) throws VideoUploadException, VideoAlreadyExistException {
        String id = this.videoSaverService.uploadVideo(file);
        String s = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "streamVideo", id, 0).build().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("videoId", s);
        return ResponseEntity.ok(map);
    }
}
