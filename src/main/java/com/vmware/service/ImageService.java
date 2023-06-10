package com.vmware.service;

import com.vmware.modal.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    
    List<Image> findByAlbumId(Long albumId);
    
    Image uploadImage(Long albumId, MultipartFile file, String title, String description) throws IOException;

    Image findByImageId(Long imgId);

    void uploadImages(MultipartFile[] image);

    void deleteImageBy(Long id);
}
