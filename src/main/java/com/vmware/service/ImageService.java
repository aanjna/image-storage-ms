package com.vmware.service;

import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    
    List<Image> findByAlbumId(Long albumId);
    
    Image uploadImage(Long albumId, MultipartFile file, String title, String description) throws IOException;

    Image findImageById(Long imgId);

    void uploadImages(MultipartFile[] image) throws IOException;

    void deleteImageById(Long id);

    Image findImageByIdFromImageAlbum(Long imageId, ImageAlbum album);
}
