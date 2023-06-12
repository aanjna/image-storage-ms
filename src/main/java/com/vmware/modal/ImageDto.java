package com.vmware.modal;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String title;

    private String description;

    private String filePath;

    private Long uploadDate;

    private byte[] imageSize;

    ImageAlbum imageAlbum;

}
