package com.odeyalo.analog.netflix.repository;

import com.odeyalo.analog.netflix.entity.GenericFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericFileEntityRepository extends JpaRepository<GenericFileEntity, String> {
}
