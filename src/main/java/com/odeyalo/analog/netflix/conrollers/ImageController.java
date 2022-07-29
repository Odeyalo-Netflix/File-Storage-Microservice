package com.odeyalo.analog.netflix.conrollers;

import com.odeyalo.analog.netflix.exceptions.ImageNotReadableException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.service.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    public ImageController(@Qualifier("localImageManager") ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource getImageById(@PathVariable("id") String imageId) throws IOException, ImageNotReadableException {
        return this.imageManager.getImageById(imageId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveImage(@RequestPart MultipartFile file) throws IOException, UploadException {
        this.logger.info("Type: {}", file.getContentType());
        String path = this.imageManager.saveImage(file);
        String s = MvcUriComponentsBuilder.fromMethodName(ImageController.class, "getImageById", path).build().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", s);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping(value = "/resize", produces = {MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE})
    public Resource resizeImage(@RequestParam String imageId, @RequestParam Integer height, @RequestParam Integer width) throws IOException, ImageNotReadableException {
        return this.imageManager.changeImageSize(imageId, height, width);
    }
}
