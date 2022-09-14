package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.LinkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Collections;

@Service
public class VideoFileInformationService extends GenericFileInformationService {

    private final String host;
    public VideoFileInformationService(GenericFileEntityRepository repository,
                                       @Value("${app.host}") String host) {
        super(repository);
        this.host = host;
    }

    @Override
    public FileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException {
        FileInformationResponseDTO videoInfo = super.getInformation(fileId);
        String url = "/api/v1/video/watch?videoId=" + videoInfo.getFileId();
        String link = host + url;
        videoInfo.setUrl(url);
        videoInfo.setLinks(Collections.singletonList(new LinkResponse(link, "GET")));
        return videoInfo;
    }
}
