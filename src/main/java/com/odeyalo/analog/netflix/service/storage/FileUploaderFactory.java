package com.odeyalo.analog.netflix.service.storage;

public interface FileUploaderFactory {
    /**
     * Returns file uploader by strategy
     *
     * @return - FileUploader
     */
    FileUploader getFileUploader();

}
