package com.odeyalo.analog.netflix.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class GenericFileEntity {
    @Id
    @GeneratedValue(generator = "image-generator")
    @GenericGenerator(name = "image-generator", strategy = "com.odeyalo.analog.netflix.support.jpa.PrefixIdentifierGenerator")
    private String id;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private Long fileCreated;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String type;
}
