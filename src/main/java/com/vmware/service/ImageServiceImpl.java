package com.vmware.service;

import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import com.vmware.repository.AlbumRepository;
import com.vmware.repository.ImageRepository;
import com.vmware.utills.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_FOLDER = "/imageAlbum";

    ImageUtils imageUtils;
    private ImageRepository imageRepository;
    private AlbumRepository albumRepository;

    public ImageServiceImpl(ImageRepository imageRepository, AlbumRepository albumRepository) {
        this.imageRepository = imageRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Image> findByAlbumId(Long albumId) {
        Optional<ImageAlbum> album = albumRepository.findById(albumId);
        List<Image> images = album.get().getImages();
        return images;
    }

    @Override
    public Image findByAlbumIdAndImageId(Long albumId, Long imgId) {
        Optional<ImageAlbum> album = albumRepository.findById(albumId);
        Image image = imageRepository.findByImageAlbumId(album.get().getId());
        return image;
    }

    @Override
    public Image uploadImages(MultipartFile image) {
        String fileName = image.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
        String filePath = IMAGE_FOLDER + File.separator + randomFileName;

        File f = new File(IMAGE_FOLDER);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            Files.copy(image.getInputStream(), Paths.get(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image imageObj = new Image();
        imageObj.setFilePath(filePath);
        ImageAlbum album = albumRepository.getAlbumById(1L);
        imageObj.setImageAlbum(album);
        imageObj.setTitle(image.getOriginalFilename());
        imageObj.setDescription("");
        imageObj.setUploadDate(System.currentTimeMillis());
        imageRepository.save(imageObj);
        return imageObj;
    }

    @Override
    public void deleteImageBy(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image uploadImage(Long albumId, MultipartFile file, String title, String description) throws IOException {
        Image image = new Image();
        ImageAlbum imageAlbum = albumRepository.getAlbumById(albumId);
        if (!ObjectUtils.isEmpty(imageAlbum)) {
            image.setImageAlbum(imageAlbum);
        } else {
            image.setImageAlbum(new ImageAlbum());
            throw new FileNotFoundException();
        }
        image.setTitle(title);
        image.setDescription(description);
        String filePath = uploadImageToAlbumStorage(file);
        image.setFilePath(filePath);

        return imageRepository.save(image);
    }

    public String uploadImageToAlbumStorage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = file + IMAGE_FOLDER + fileName;
        File f = new File(IMAGE_FOLDER);
        if (!f.exists()) {
            f.mkdir();
        }
        imageUtils.saveImage(file, IMAGE_FOLDER, fileName);
        return filePath;
    }


}
