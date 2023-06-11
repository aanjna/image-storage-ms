package com.vmware.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "albums")
@Data
public class ImageAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "imageAlbum")
    private List<Image> images;

}

