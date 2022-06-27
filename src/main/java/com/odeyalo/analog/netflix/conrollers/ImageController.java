package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.dto.response.ImageUrlResponseDTO;
import com.odeyalo.analog.netflix.dto.response.UserImagesResponseDTO;
import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.service.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    public ImageController(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    @GetMapping(value = "/", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource getImageById(@RequestParam("id") String imageId) throws IOException, ImageNotReadableException {
        return this.imageManager.getImageById(imageId);
    }
    @GetMapping(value = "/poster", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Resource getPoster(@RequestParam String posterId) throws IOException, ImageNotReadableException {
        return this.imageManager.getImageByRemoteImageId(posterId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveImage(@RequestPart MultipartFile file, @RequestParam(required = false) String userId) throws IOException, UploadException {
        this.imageManager.saveUserImage(file, userId);
        this.logger.info("Type: {}", file.getContentType());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save/poster")
    public ResponseEntity<?> savePoster(@RequestPart MultipartFile file, String posterId) throws IOException, UploadException {
        this.logger.info("Type: {}", file.getContentType());
        String imageUrl = this.imageManager.saveVideoImage(file, posterId);
        return ResponseEntity.ok().body(new ImageUrlResponseDTO(imageUrl));
    }


    @GetMapping(value = "/resize", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource resizeImage(@RequestParam String imageId, @RequestParam Integer height, @RequestParam Integer width) throws IOException, ImageNotReadableException {
        return this.imageManager.changeImageSize(imageId, height, width);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllImagesByUserId(@RequestParam String userId) throws ImageNotReadableException {
        List<String> images = this.imageManager.getImagesByUserId(userId);
        return new ResponseEntity<>(new UserImagesResponseDTO(images, userId), HttpStatus.OK);
    }
}
