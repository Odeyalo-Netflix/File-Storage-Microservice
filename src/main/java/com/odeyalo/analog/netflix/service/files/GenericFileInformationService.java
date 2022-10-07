package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.entity.GenericFileEntity;
import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;

@Service
public class GenericFileInformationService implements FileInformationService {
    protected GenericFileEntityRepository repository;
    protected String servletContextPath;
    protected String host;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GenericFileInformationService(GenericFileEntityRepository repository,
                                         @Value("${app.host}") String host,
                                         @Value("#{servletContext.contextPath}") String servletContextPath) {
        this.repository = repository;
        this.servletContextPath = servletContextPath;
        this.host = host;
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

    /**
     * Return http link to the file. Example: https://static.application.files/{contextPath}/{url}/{imageId}
     *
     * @param args - args id to create link
     * @return - string with built url
     */
    protected String getLink(Class<?> controller, String methodName, Object... args) {
        return MvcUriComponentsBuilder
                .fromMethodName(UriComponentsBuilder.fromHttpUrl(host).path(servletContextPath), controller, methodName, args)
                .toUriString();
    }
}
