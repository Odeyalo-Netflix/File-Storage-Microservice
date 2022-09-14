package com.odeyalo.analog.netflix.service.files;


import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;

import java.io.FileNotFoundException;

/**
 * Returns an information about given file
 */
public interface FileInformationService {

    FileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException;

}
