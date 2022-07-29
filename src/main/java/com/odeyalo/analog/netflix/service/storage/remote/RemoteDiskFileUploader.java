package com.odeyalo.analog.netflix.service.storage.remote;

import com.odeyalo.analog.netflix.dto.enums.RemoteDiskType;
import com.odeyalo.analog.netflix.service.storage.FileUploader;

public interface RemoteDiskFileUploader extends FileUploader {

    RemoteDiskType getRemoteDiskType();

}
