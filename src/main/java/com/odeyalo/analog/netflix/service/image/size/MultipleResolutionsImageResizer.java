package com.odeyalo.analog.netflix.service.image.size;

import com.odeyalo.analog.netflix.service.files.ResizedFileSavingResult;

import java.awt.image.BufferedImage;
import java.util.List;

public interface MultipleResolutionsImageResizer {

    String DOT = ".";

    String JPG_EXTENSION = "jpg";

    String PNG_EXTENSION = "png";

    /**
     * @param image - image to resize
     * @param data  - list of required resolutions for image
     * @return - list of ImageSavingResult
     */
    List<ResizedFileSavingResult> resizeImage(BufferedImage image, List<ImageResizeData> data);

    /**
     * Resize given image to multiple images with different height/width
     *
     * @param path      - path to folder where image should be saved
     * @param extension - file extension that will be used during saving
     * @param image     - image to resize
     * @param data      - list of required resolutions for image
     */
    List<ResizedFileSavingResult> resizeImage(String path, String extension, BufferedImage image, List<ImageResizeData> data);

}
