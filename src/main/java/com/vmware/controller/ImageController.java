package com.vmware.controller;

import com.vmware.modal.Image;
import com.vmware.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String> uploadImage(
            @PathVariable("albumId") Long albumId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("title") String title,
            @RequestParam("description") String description) {
        try {
            Image saveImage = imageService.uploadImage(albumId, image, title, description);

            return ResponseEntity.ok("Image uploaded successfully. Image: " + saveImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImg(@RequestParam("image") MultipartFile image) {
        try {
            Image saveImage = imageService.uploadImages(image);
            return ResponseEntity.ok("Image uploaded successfully. Image: " + saveImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{albumId}/images/{imgId}")
    public ResponseEntity<Image> getImagesById(@PathVariable("albumId") Long albumId, @PathVariable("imgId") Long imgId) {
        Image images = imageService.findByAlbumIdAndImageId(albumId, imgId);
        return ResponseEntity.ok(images);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(@PathVariable("albumId") Long albumId) {
        List<Image> images = imageService.findByAlbumId(albumId);
        return ResponseEntity.ok(images);
    }

}

