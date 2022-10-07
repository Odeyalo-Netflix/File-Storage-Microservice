package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.conrollers.ImageController;
import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.support.clients.filestorage.dto.FileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.ImageFileInformationResponseDTO;
import com.odeyalo.support.clients.filestorage.dto.LinkResponse;
import com.odeyalo.support.clients.filestorage.dto.ResizedImageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageFileInformationService extends GenericFileInformationService {
    private final ImageRepository imageRepository;


    @Autowired
    public ImageFileInformationService(GenericFileEntityRepository repository,
                                       @Value("${app.host}") String host,
                                       @Value("#{servletContext.contextPath}") String servletContextPath,
                                       ImageRepository imageRepository) {
        super(repository, host, servletContextPath);
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void afterInit() {
        this.logger.info("Server host is: {} and current servlet path is: {}", host, servletContextPath);
    }

    @Override
    public ImageFileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException {
        FileInformationResponseDTO info = super.getInformation(fileId);
        Image image = this.imageRepository.findById(info.getFileId()).orElseThrow(FileNotFoundException::new);
        List<Image> resizedImages = image.getResizedImages();

        String url = buildGetImageByIdUrl(fileId, servletContextPath, UriComponentsBuilder.newInstance());
        info.setUrl(url);

        String link = getLink(ImageController.class, "getImageById", fileId);
        info.setLinks(Collections.singletonList(new LinkResponse(link, "GET")));

        List<ResizedImageResponseDTO> list = getResizedImageResponseDTOS(resizedImages);
        return new ImageFileInformationResponseDTO(info, list);
    }


    protected List<ResizedImageResponseDTO> getResizedImageResponseDTOS(List<Image> resizedImages) {
        return resizedImages.stream().map(x -> {
            String id = x.getId();
            String link = getLink(ImageController.class, "getImageById", id);
            return new ResizedImageResponseDTO(
                    id,
                    x.getWidth(),
                    x.getHeight(),
                    Collections.singletonList(new LinkResponse(link, "GET")));
        }).collect(Collectors.toList());
    }

    protected String buildGetImageByIdUrl(String fileId, String path, UriComponentsBuilder uriComponentsBuilder) {
        return MvcUriComponentsBuilder
                .fromMethodName(uriComponentsBuilder.path(path), ImageController.class, "getImageById", fileId)
                .toUriString();
    }
}
