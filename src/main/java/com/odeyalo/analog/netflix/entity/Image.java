package com.odeyalo.analog.netflix.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends GenericFileEntity {
    @Column(nullable = false)
    private Integer height;
    @Column(nullable = false)
    private Integer width;
    /**
     * Resized versions of the image.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> resizedImages;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ImageStorageType storageType;


    public void addResizedFileEntity(Image entity) {
        resizedImages.add(entity);
    }

    public void addResizedFileEntities(List<Image> entity) {
        resizedImages.addAll(entity);
    }
}
