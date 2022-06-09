package com.odeyalo.analog.netflix.service.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileStorage implements FileStorage {
    private final Logger logger = LoggerFactory.getLogger(LocalFileStorage.class);
    private static final String FOLDER_NAME = "D:\\videos\\";

    @Override
    public String save(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String path = FOLDER_NAME + file.getOriginalFilename();
        Files.write(Path.of(path), bytes);
        this.logger.info("File with name: {} saved", file.getName());
        return path;
    }
}
