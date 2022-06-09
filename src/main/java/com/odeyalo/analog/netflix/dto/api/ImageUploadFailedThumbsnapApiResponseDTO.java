package com.odeyalo.analog.netflix.dto.api;

public class ImageUploadFailedThumbsnapApiResponseDTO extends GenericThumbsnapMessageDTO {
    private ImageUploadFailed error;

    public ImageUploadFailed getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ImageUploadFailedThumbsnapApiResponseDTO{" +
                "success=" + success +
                ", status=" + status +
                ", error=" + error +
                '}';
    }

    public void setError(ImageUploadFailed error) {
        this.error = error;
    }

    public static class ImageUploadFailed {
        private String message;

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "ImageUploadFailed{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }
}
