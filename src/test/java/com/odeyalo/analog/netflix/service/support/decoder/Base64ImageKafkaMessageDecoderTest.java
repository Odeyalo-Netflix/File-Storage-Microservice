package com.odeyalo.analog.netflix.service.support.decoder;

import com.odeyalo.analog.netflix.dto.support.ImageMimeTypeMapper;
import com.odeyalo.analog.netflix.service.decoder.Base64ImageKafkaMessageDecoder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class Base64ImageKafkaMessageDecoderTest {
    Base64ImageKafkaMessageDecoder decoder = new Base64ImageKafkaMessageDecoder();
    private static final String CURRENT_PATH = System.getProperty("user.dir");
    private static final String JPG_FILE_PATH = new StringBuilder(CURRENT_PATH)
            .append(File.separator)
            .append("src")
            .append(File.separator)
            .append("test")
            .append(File.separator)
            .append("test-files")
            .append(File.separator)
            .append("jpg-test.jpg").toString();
    private static final String SAVE_FILE_DIRECTORY = new StringBuilder(CURRENT_PATH)
            .append(File.separator)
            .append("src")
            .append(File.separator)
            .append("test")
            .append(File.separator)
            .append("test-files")
            .append(File.separator)
            .append("saved")
            .append(File.separator)
            .toString();


    @BeforeAll
    static void beforeAll() {
        System.out.println("Current directory: " + CURRENT_PATH);
        File file = new File(SAVE_FILE_DIRECTORY);
        file.mkdir();
    }
    @Test
    void encode() throws IOException {
        File file = new File(JPG_FILE_PATH);
        String image = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        InputStream decode = this.decoder.decode(image);
        assertNotNull(decode);
        String extension = URLConnection.guessContentTypeFromStream(decode);
        String fileExtension = ImageMimeTypeMapper.getExtension(extension);
        assertNotNull(fileExtension);
        File fileToSave = new File(new StringBuilder().append(SAVE_FILE_DIRECTORY).append("file").append(fileExtension).toString());
        Files.copy(decode, Paths.get(fileToSave.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterAll
    static void afterAll() throws IOException {
        FileUtils.deleteDirectory(new File(SAVE_FILE_DIRECTORY));
    }
}
