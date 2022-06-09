package com.odeyalo.analog.netflix.dto.api;

public class ThumbsnapImageRemoteStorageResponseDTO {
    private ImageUploadedSuccessfulDTO data;
    private boolean success;
    private int status;


    public ImageUploadedSuccessfulDTO getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "ThumbsnapImageRemoteStorageResponseDTO{" +
                "data=" + data +
                ", success=" + success +
                ", status=" + status +
                '}';
    }

    public static class ImageUploadedSuccessfulDTO {
        private String id;
        private String url;
        private String media;
        private String thumb;
        private String width;
        private String height;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getMedia() {
            return media;
        }

        public String getThumb() {
            return thumb;
        }

        public String getWidth() {
            return width;
        }

        public String getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "ImageUploadedSuccessfulDTO{" +
                    "id='" + id + '\'' +
                    ", url='" + url + '\'' +
                    ", media='" + media + '\'' +
                    ", thumb='" + thumb + '\'' +
                    ", width='" + width + '\'' +
                    ", height='" + height + '\'' +
                    '}';
        }
    }
}
