package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.annotation.ValidVideoFIle;
import com.odeyalo.analog.netflix.exceptions.VideoAlreadyExistException;
import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;
import com.odeyalo.analog.netflix.exceptions.VideoUploadException;
import com.odeyalo.analog.netflix.service.files.FileInformationService;
import com.odeyalo.analog.netflix.service.video.VideoManager;
import com.odeyalo.analog.netflix.service.video.VideoStreamingService;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.LinkResponse;
import com.odeyalo.support.clients.filestorage.dto.SuccessUploadVideoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/video")
@Validated
public class VideoController {
    private final VideoManager videoManager;
    private final VideoStreamingService<ResponseEntity<ResourceRegion>> streamService;
    private final FileInformationService fileInformationService;

    @Autowired
    public VideoController(VideoManager videoManager, VideoStreamingService<ResponseEntity<ResourceRegion>> streamService,
                           @Qualifier("videoFileInformationService") FileInformationService fileInformationService) {
        this.videoManager = videoManager;
        this.streamService = streamService;
        this.fileInformationService = fileInformationService;
    }

    @GetMapping(value = "/watch", produces = "application/octet-stream")
    public ResponseEntity<ResourceRegion> streamVideo(@RequestParam String videoId, @RequestHeader(name = "range", required = false) String range) throws VideoNotFoundException, IOException {
        return this.streamService.stream(videoId, range);
    }

    @GetMapping(value = "/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileInformationResponseDTO> streamVideo(@PathVariable String id) throws VideoNotFoundException, IOException {
        FileInformationResponseDTO information = this.fileInformationService.getInformation(id);
        return new ResponseEntity<>(information, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> saveVideo(@ValidVideoFIle @RequestPart(name = "video") MultipartFile file) throws VideoUploadException, VideoAlreadyExistException {
        String id = this.videoManager.saveVideo(file);
        String url = MvcUriComponentsBuilder.fromMethodName(VideoController.class, "streamVideo", id, 0).build().toString();
        SuccessUploadVideoResponseDTO dto = new SuccessUploadVideoResponseDTO(id,
                Collections.singletonList(new LinkResponse(url, HttpMethod.GET.name())));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{videoId}")
    public ResponseEntity<?> deleteVideo(@PathVariable String videoId) {
        this.videoManager.deleteVideo(videoId);
        return ResponseEntity.status(204).build();
    }
}
