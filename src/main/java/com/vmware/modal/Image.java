package com.vmware.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long uploadDate;

    @Column(nullable = false)
    private byte[] imageSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    ImageAlbum imageAlbum;

}