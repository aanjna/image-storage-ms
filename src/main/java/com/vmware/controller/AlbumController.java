package com.vmware.controller;

import com.vmware.modal.ImageAlbum;
import com.vmware.modal.AlbumRequestDto;
import com.vmware.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
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
}

