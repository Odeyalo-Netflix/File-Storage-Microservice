package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.entity.GenericFileEntity;
import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class GenericFileInformationService implements FileInformationService {
    private final GenericFileEntityRepository repository;

    @Autowired
    public GenericFileInformationService(GenericFileEntityRepository repository) {
        this.repository = repository;
    }


    /**
     * Returns generic information about file
     * @param fileId - file id
     * @return - generic info about file entity
     * @throws FileNotFoundException - if file or entity not exist
     */
    @Override
    public FileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException {
        GenericFileEntity fileEntity = this.repository.findById(fileId).orElseThrow(FileNotFoundException::new);
        return FileInformationResponseDTO.builder()
                .fileId(fileEntity.getId())
                .fileCreated(fileEntity.getFileCreated())
                .size(fileEntity.getSize())
                .type(fileEntity.getType())
                .build();
    }
}
