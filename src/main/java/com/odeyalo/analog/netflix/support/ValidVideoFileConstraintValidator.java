package com.odeyalo.analog.netflix.support;

import com.odeyalo.analog.netflix.annotation.ValidVideoFIle;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ValidVideoFileConstraintValidator implements ConstraintValidator<ValidVideoFIle, MultipartFile> {
    private final Set<String> mimeTypes = new HashSet<>(6);

    public ValidVideoFileConstraintValidator() {
        this.mimeTypes.add("video/x-flv");
        this.mimeTypes.add("video/mp4");
        this.mimeTypes.add("application/x-mpegURL");
        this.mimeTypes.add("video/MP2T");
        this.mimeTypes.add("video/3gpp");
        this.mimeTypes.add("video/webm");
        this.mimeTypes.add("video/quicktime");
        this.mimeTypes.add("video/x-msvideo");
        this.mimeTypes.add("video/x-ms-wmv");
    }

    @Override
    public void initialize(ValidVideoFIle constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        String contentType = multipartFile.getContentType();
        Assert.notNull(contentType, "Content type cannot be null!");
        if (!isSupportedContentType(contentType)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only video file types are allowed.")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isSupportedContentType(String contentType) {
        return mimeTypes.contains(contentType);
    }
}
