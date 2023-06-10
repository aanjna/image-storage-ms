package com.vmware.repository;

import com.vmware.modal.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByImageAlbumId(Long id);
}
