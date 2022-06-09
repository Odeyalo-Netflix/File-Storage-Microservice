package com.odeyalo.analog.netflix.dto.support;

import java.util.HashMap;
import java.util.Map;

/**
 * Map Http image MIME type to default extensions
 */

public class ImageMimeTypeMapper {
    private static final Map<String, String> mimeTypes = new HashMap<>();

    static {
        mimeTypes.put("image/gif", ".gif");
        mimeTypes.put("image/vnd.microsoft.icon", ".ico");
        mimeTypes.put("image/jpeg", ".jpeg");
        mimeTypes.put("image/avif", ".avif");
        mimeTypes.put("image/png", ".png");
        mimeTypes.put("image/svg+xml", ".svg");
        mimeTypes.put("application/octet-stream", ".jfif");
    }

    public static String getExtension(String mimeType) {
        return mimeTypes.get(mimeType);
    }
}
