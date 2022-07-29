package com.odeyalo.analog.netflix.repository;

import com.odeyalo.analog.netflix.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
