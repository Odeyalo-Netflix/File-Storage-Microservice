package com.odeyalo.analog.netflix.service.image;

import com.odeyalo.analog.netflix.entity.Image;
import com.odeyalo.analog.netflix.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageRemoveServiceImpl implements ImageRemoveService {
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(ImageRemoveServiceImpl.class);

    public ImageRemoveServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void delete(String id) {
        Optional<Image> optional = this.imageRepository.findById(id);
        if (optional.isPresent()) {
            try {
                Image image = optional.get();
                String path = image.getPath();
                Files.deleteIfExists(Paths.get(path));
                this.imageRepository.delete(image);
            } catch (IOException ex) {
                this.logger.error("Error during image remove process", ex);
            }
        }
    }
}
