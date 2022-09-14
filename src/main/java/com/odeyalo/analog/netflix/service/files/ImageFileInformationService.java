package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.LinkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collections;

@Service
public class ImageFileInformationService extends GenericFileInformationService {
    private final String host;

    public ImageFileInformationService(GenericFileEntityRepository repository,
                                       @Value("${app.host}") String host) {
        super(repository);
        this.host = host;
    }

    @Override
    public FileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException {
        FileInformationResponseDTO info = super.getInformation(fileId);
        String url = "/api/v1/image/" + fileId;
        String link = host + url;
        info.setUrl(url);
        info.setLinks(Collections.singletonList(new LinkResponse(link, "GET")));
        return info;
    }
}
