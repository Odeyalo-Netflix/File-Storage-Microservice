package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.entity.ImageStorageType;
import com.odeyalo.analog.netflix.exceptions.UploadException;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import com.odeyalo.analog.netflix.service.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ThumbsnapRemoteImageSaverService implements ImageSaverService {
    private final FileStorage fileStorage;
    private final ImageRepository imageRepository;

    @Autowired
    public ThumbsnapRemoteImageSaverService(@Qualifier("thumbsnapSiteRemoteDiskFileStorage") FileStorage fileStorage, ImageRepository imageRepository) {
        this.fileStorage = fileStorage;
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveUserImage(MultipartFile file, String userId) throws UploadException, IOException {
        String path = this.fileStorage.save(file);
        Image image = Image.builder()
                .userId(userId)
                .path(path)
                .type(ImageStorageType.REMOTE)
                .build();
        this.imageRepository.save(image);
    }

    @Override
    public String saveVideoImage(MultipartFile file, String remoteImageId) throws UploadException, IOException {
        String path = this.fileStorage.save(file);
        Image image = Image.builder()
                .remoteImageId(remoteImageId)
                .type(ImageStorageType.REMOTE)
                .path(path)
                .build();
        this.imageRepository.save(image);
        return path;
    }
}
