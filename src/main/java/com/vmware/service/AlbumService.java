package com.vmware.service;

import com.vmware.modal.ImageAlbum;

public interface AlbumService {
    ImageAlbum createAlbum(String name);

    void deleteAlbumById(Long id);

    ImageAlbum findById(Long albumId);
}
