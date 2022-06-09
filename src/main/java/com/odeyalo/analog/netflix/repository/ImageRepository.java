package com.odeyalo.analog.netflix.repository;

import com.odeyalo.analog.netflix.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findAllByUserId(String userId);

    Optional<Image> findImageByRemoteImageId(String remoteImageId);
}
