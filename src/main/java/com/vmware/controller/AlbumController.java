package com.vmware.controller;

import com.vmware.modal.AlbumRequestDto;
import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import com.vmware.service.AlbumService;
import com.vmware.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private AlbumService albumService;
    private ImageService imageService;

    public AlbumController(AlbumService albumService, ImageService imageService) {
        this.albumService = albumService;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageAlbum> createAlbum(@RequestBody AlbumRequestDto albumRequest) {
        String albumName = albumRequest.getName();
        ImageAlbum createdImageAlbum = albumService.createAlbum(albumName);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImageAlbum);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") Long id) {
        albumService.deleteAlbumById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{albumId}/images/{imageId}")
    public ResponseEntity<Image> getImageByIdFromAlbum(@PathVariable("albumId") Long albumId, @PathVariable("imageId") Long imageId) {

        ImageAlbum album = albumService.findById(albumId);

        if (ObjectUtils.isEmpty(album)) {
            return ResponseEntity.notFound().build();
        }

        Image image = imageService.findImageByIdFromImageAlbum(imageId, album);

        if (ObjectUtils.isEmpty(image)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(image);
    }

}

