package com.odeyalo.analog.netflix.repository;

import com.odeyalo.analog.netflix.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    Optional<Video> findVideoById(String videoId);
}
