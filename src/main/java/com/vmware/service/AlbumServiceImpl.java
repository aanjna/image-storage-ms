package com.vmware.service;

import com.vmware.modal.ImageAlbum;
import com.vmware.repository.AlbumRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public ImageAlbum createAlbum(String name) {
        ImageAlbum imageAlbum = new ImageAlbum();
        imageAlbum.setName(name);
        return albumRepository.save(imageAlbum);
    }

    @Override
    public void deleteAlbumById(Long id) {
        albumRepository.deleteById(id);
    }

}
