package com.odeyalo.analog.netflix.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String remoteImageId;
    private String path;
    private ImageStorageType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemoteImageId() {
        return remoteImageId;
    }

    public void setRemoteImageId(String remoteImageId) {
        this.remoteImageId = remoteImageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageStorageType getType() {
        return type;
    }

    public void setType(ImageStorageType type) {
        this.type = type;
    }

    public static ImageBuilder builder() {
        return new ImageBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static final class ImageBuilder {
        private String id;
        private String userId;
        private String path;
        private String remoteImageId;
        private ImageStorageType type;

        private ImageBuilder() {
        }


        public ImageBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ImageBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ImageBuilder remoteImageId(String remoteImageId) {
            this.remoteImageId = remoteImageId;
            return this;
        }

        public ImageBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ImageBuilder type(ImageStorageType type) {
            this.type = type;
            return this;
        }

        public Image build() {
            Image image = new Image();
            image.setId(id);
            image.setRemoteImageId(remoteImageId);
            image.setUserId(userId);
            image.setPath(path);
            image.setType(type);
            return image;
        }
    }
}
