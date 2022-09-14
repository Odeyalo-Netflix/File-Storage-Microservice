package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.service.files.FileInformationService;
import com.odeyalo.analog.netflix.service.image.ImageManager;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.ImageSuccessfulUploadedResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.LinkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final FileInformationService fileInformationService;

    public ImageController(@Qualifier("localImageManager") ImageManager imageManager,
                           @Qualifier("imageFileInformationService") FileInformationService fileInformationService) {
        this.imageManager = imageManager;
        this.fileInformationService = fileInformationService;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource getImageById(@PathVariable("id") String imageId) throws IOException, ImageNotReadableException {
        return this.imageManager.getImageById(imageId);
    }

    @GetMapping(value = "/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getImageInfoById(@PathVariable("id") String imageId) throws IOException, ImageNotReadableException {
        FileInformationResponseDTO info = this.fileInformationService.getInformation(imageId);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveImage(@RequestPart MultipartFile file) throws IOException, UploadException {
        this.logger.info("Type: {}", file.getContentType());
        String id = this.imageManager.saveImage(file);
        String link = MvcUriComponentsBuilder.fromMethodName(ImageController.class, "getImageById", id).build().toString();
        ImageSuccessfulUploadedResponseDTO dto = new ImageSuccessfulUploadedResponseDTO(id, Collections.singletonList(
                new LinkResponse(link, "GET")
        ));
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/resize", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource resizeImage(@RequestParam String imageId, @RequestParam Integer height, @RequestParam Integer width) throws IOException, ImageNotReadableException {
        return this.imageManager.changeImageSize(imageId, height, width);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<?> delete(@PathVariable String imageId) {
        this.imageManager.deleteImage(imageId);
        return ResponseEntity.status(204).build();
    }
}
