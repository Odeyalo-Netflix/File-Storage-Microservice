package com.odeyalo.analog.netflix.service.image.size;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class MultipleResolutionsImageResizerImplTest {
    private MultipleResolutionsImageResizerImpl imageResizer = new MultipleResolutionsImageResizerImpl(new ImageResizerImpl(), "C:\\Users\\thepr_2iz2cnv\\Desktop\\resized\\");

    @Test
    void resizeImage() throws IOException {
        String path = "C:\\Users\\thepr_2iz2cnv\\Desktop\\images\\1664034285435.jpg";
        BufferedImage image = ImageIO.read(new FileInputStream(path));
        List<ImageResizeData> list = Arrays.asList(new ImageResizeData(50, 50), new ImageResizeData(200, 200), new ImageResizeData(400, 400));
        imageResizer.resizeImage(image, list);
    }
}
