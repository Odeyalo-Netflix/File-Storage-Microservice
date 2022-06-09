package com.odeyalo.analog.netflix.service.storage;

import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorage {

    /**
     *
     * @param file - file to saving
     * @return url or path to file resource
     * @throws IOException
     */
    String save(MultipartFile file) throws UploadException, IOException;

}
