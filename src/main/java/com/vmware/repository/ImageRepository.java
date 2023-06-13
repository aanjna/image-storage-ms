package com.vmware.repository;

import com.vmware.modal.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByIdAndAlbumId(Long imageId, Long id);
}
