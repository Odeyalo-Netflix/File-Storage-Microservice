package com.odeyalo.analog.netflix.repository;

import com.odeyalo.analog.netflix.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, String> {

    Optional<Video> findVideoByVideoId(String videoId);
}
