package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.service.video.VideoStreamingService;
import com.odeyalo.analog.netflix.service.video.VideoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    private final VideoUploadService videoUploadService;
    private final VideoStreamingService<ResponseEntity<ResourceRegion>> streamService;

    @Autowired
    public VideoController(VideoUploadService videoUploadService, VideoStreamingService<ResponseEntity<ResourceRegion>> streamService) {
        this.videoUploadService = videoUploadService;
        this.streamService = streamService;
    }

    @GetMapping(value = "/watch", produces = "application/octet-stream")
    public ResponseEntity<ResourceRegion> streamVideo(@RequestParam String videoId, @RequestHeader(name = "range", required = false) String range) throws VideoNotFoundException, IOException {
        return this.streamService.stream(videoId, range);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> saveVideo(@RequestParam String videoId, @RequestPart(name = "video") MultipartFile file) throws VideoUploadException, VideoAlreadyExistException {
        this.videoUploadService.uploadVideo(file, videoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
