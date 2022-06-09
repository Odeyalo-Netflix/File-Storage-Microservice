package com.odeyalo.analog.netflix.service.storage.remote;

import com.odeyalo.analog.netflix.dto.enums.RemoteDiskType;
import com.odeyalo.analog.netflix.service.storage.FileStorage;

public interface RemoteDiskFileStorage extends FileStorage {

    RemoteDiskType getRemoteDiskType();

}
