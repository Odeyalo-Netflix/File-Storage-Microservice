package com.odeyalo.analog.netflix.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(unique = true, nullable = false, updatable = false)
    private String videoId; //video id from other microservice
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static VideoBuilder builder() {
        return new VideoBuilder();
    }

    public static final class VideoBuilder {
        private String id;
        private String videoId;
        private String path;

        private VideoBuilder() {}

        public VideoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public VideoBuilder videoId(String videoId) {
            this.videoId = videoId;
            return this;
        }

        public VideoBuilder path(String path) {
            this.path = path;
            return this;
        }

        public Video build() {
            Video video = new Video();
            video.setId(id);
            video.setVideoId(videoId);
            video.setPath(path);
            return video;
        }
    }
}
