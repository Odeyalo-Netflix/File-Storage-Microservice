package com.odeyalo.analog.netflix.service.files;

import com.odeyalo.analog.netflix.conrollers.VideoController;
import com.odeyalo.analog.netflix.entity.Video;
import com.odeyalo.analog.netflix.repository.GenericFileEntityRepository;
import com.odeyalo.analog.netflix.repository.VideoRepository;
import com.odeyalo.support.clients.filestorage.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoFileInformationService extends GenericFileInformationService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoFileInformationService(GenericFileEntityRepository repository,
                                       @Value("${app.host}") String host,
                                       @Value("#{servletContext.contextPath}") String servletContextPath, VideoRepository videoRepository) {
        super(repository, host, servletContextPath);
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoFileInformationResponseDTO getInformation(String fileId) throws FileNotFoundException {
        FileInformationResponseDTO genericInfo = super.getInformation(fileId);
        String url = buildGetVideoByIdUrl(fileId, servletContextPath, UriComponentsBuilder.newInstance());
        String link = getLink(VideoController.class, "streamVideo", fileId, "");
        genericInfo.setUrl(url);
        genericInfo.setLinks(Collections.singletonList(new LinkResponse(link, "GET")));
        Video video = videoRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
        return new VideoFileInformationResponseDTO(genericInfo, getResizedVideoFileResponseDTO(video));
    }



    List<ResizedVideoFileResponseDTO> getResizedVideoFileResponseDTO(Video video)  {
        return video.getResizedVideos().stream().map(v -> new ResizedVideoFileResponseDTO(new VideoResolution(v.getWidth(), v.getHeight()),
                Collections.singletonList(new LinkResponse(getLink(VideoController.class, "streamVideo", v.getId(), ""), "GET")))).collect(Collectors.toList());
    }

    protected String buildGetVideoByIdUrl(String videoId, String path, UriComponentsBuilder uriComponentsBuilder) {
        return MvcUriComponentsBuilder
                .fromMethodName(uriComponentsBuilder.path(path), VideoController.class, "streamVideo", videoId, "")
                .toUriString();
    }
}
