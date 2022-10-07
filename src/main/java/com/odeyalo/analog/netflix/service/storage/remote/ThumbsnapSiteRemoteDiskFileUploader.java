package com.odeyalo.analog.netflix.service.storage.remote;

import com.odeyalo.analog.netflix.dto.api.ImageUploadedSuccessThumbsnapApiResponseDTO;
import com.odeyalo.analog.netflix.dto.enums.RemoteDiskType;
import com.odeyalo.analog.netflix.exceptions.ImageUploadProcessFailedException;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ThumbsnapSiteRemoteDiskFileUploader implements RemoteDiskFileUploader {
    private static final String THUMBSNAP_SAVE_FILE_URL_VALUE = "https://thumbsnap.com/api/upload";
    private final RestTemplate restTemplate;
    private static final String UPLOAD_FILE_KEY = "media";
    private static final String TOKEN_KEY = "key";
    @Value("${app.file.saving.remote.thumbsnap.api.key}")
    private String apiKey;
    private final Logger logger = LoggerFactory.getLogger(ThumbsnapSiteRemoteDiskFileUploader.class);

    @Autowired
    public ThumbsnapSiteRemoteDiskFileUploader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String save(MultipartFile file) throws UploadException, IOException {
        HttpEntity<MultiValueMap<String, Object>> httpEntity = this.buildHttpEntity(file);
        ImageUploadedSuccessThumbsnapApiResponseDTO body = this.restTemplate.postForObject(THUMBSNAP_SAVE_FILE_URL_VALUE, httpEntity, ImageUploadedSuccessThumbsnapApiResponseDTO.class);
        this.logger.info("RESPONSE VALUE: {}", body);
        if (body != null && body.getStatus() == 200) {
            this.logger.info("Saved file. Image id: {}", body.getData().getId());
            return body.getData().getThumb();
        }
        throw new ImageUploadProcessFailedException("Image upload failed. Try again latter");
    }

    @Override
    public String save(File file) throws IOException {
        return null;
    }

    @Override
    public RemoteDiskType getRemoteDiskType() {
        return RemoteDiskType.THUMBSNAP;
    }

    private HttpEntity<MultiValueMap<String, Object>> buildHttpEntity(MultipartFile file) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        ContentDisposition disposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(file.getName())
                .build();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getName();
            }
        };
        body.add(UPLOAD_FILE_KEY, resource);
        body.add(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
        body.add(TOKEN_KEY, apiKey);
        return new HttpEntity<>(body, httpHeaders);
    }
}
