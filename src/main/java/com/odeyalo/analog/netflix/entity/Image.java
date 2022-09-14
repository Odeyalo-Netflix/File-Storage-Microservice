package com.odeyalo.analog.netflix.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends GenericFileEntity {
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ImageStorageType storageType;
}
