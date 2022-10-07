package com.odeyalo.analog.netflix.service.image.size.compressor;

import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

@Component
public class DefaultImageSizeCompressorResolver implements SizeCompressorResolver {
    public static final boolean CREATE_FOLDER_ON_FILE_SAVING = true;

    @Override
    public String compress(InputStream stream) throws IOException {
        BufferedImage image = ImageIO.read(stream);
        File compressedImageFile = new File("compress.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
    //todo create folder, save original and compressed image
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.5f);
        writer.write(null, new IIOImage(image, null, null), param);
        return compressedImageFile.getAbsolutePath();
    }
}
