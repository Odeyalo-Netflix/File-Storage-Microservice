package com.odeyalo.analog.netflix.service.storage;

import com.odeyalo.analog.netflix.support.FileNameGenerator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileUploader implements FileUploader {
    private final FileNameGenerator fileNameGenerator;
    private final Logger logger = LoggerFactory.getLogger(LocalFileUploader.class);
    @Value("${app.file.saving.default}")
    private String FOLDER_NAME;

    public LocalFileUploader(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String path = buildAndSave(extension, bytes);
        this.logger.info("File with name: {} was saved to path {}, file extension: {} ", file.getName(), path, extension);
        return path;
    }

    @Override
    public String save(File file) throws IOException {
        String absolutePath = file.getAbsolutePath();
        byte[] bytes = Files.readAllBytes(Paths.get(absolutePath));
        String extension = FilenameUtils.getExtension(absolutePath);
        String path = buildAndSave(extension, bytes);
        this.logger.info("File with name: {} was saved to path {}, file extension: {} ", file.getName(), path, extension);
        return path;
    }

    String buildAndSave(String extension, byte[] bytes) throws IOException {
        String path = new StringBuilder()
                .append(FOLDER_NAME)
                .append(fileNameGenerator.generateName())
                .append(".")
                .append(extension).toString();
        Files.write(Path.of(path), bytes);
        return path;
    }
}
