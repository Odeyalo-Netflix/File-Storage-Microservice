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
    private static final String CURRENT_PATH = Paths.get(".").toAbsolutePath().normalize().toString();
    private static final String JPG_FILE_PATH = CURRENT_PATH + "\\src\\test\\test-files\\jpg-test.jpg";
    private static final String SAVE_FILE_DIRECTORY = CURRENT_PATH + "\\src\\test\\test-files\\saved\\";

    @BeforeAll
    static void beforeAll() {
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
