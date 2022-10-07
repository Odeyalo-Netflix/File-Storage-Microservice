package com.odeyalo.analog.netflix.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "videos")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Video extends GenericFileEntity {
    private Integer height;
    private Integer width;
    @OneToMany
    private List<Video> resizedVideos;


    public void addResizedVideo(Video video) {
        resizedVideos.add(video);
    }

    public void addResizedVideos(List<Video> videos) {
        resizedVideos.addAll(videos);
    }
}
