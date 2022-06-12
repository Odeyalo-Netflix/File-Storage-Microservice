package com.odeyalo.analog.netflix.service.storage;

import com.odeyalo.analog.netflix.service.support.FileNameGenerator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class LocalFileStorage implements FileStorage {
    private final FileNameGenerator fileNameGenerator;
    private final Logger logger = LoggerFactory.getLogger(LocalFileStorage.class);
    private static final String FOLDER_NAME = "D:\\videos\\";

    public LocalFileStorage(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        this.logger.info("Start file saving with name: {}", file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String path = new StringBuilder()
                .append(FOLDER_NAME)
                .append(fileNameGenerator.generateName())
                .append(".")
                .append(extension).toString();
        Files.write(Path.of(path), bytes);
        this.logger.info("File with name: {} was saved to path {}, file extension: {} ", file.getName(), path, extension);
        return path;
    }
}
