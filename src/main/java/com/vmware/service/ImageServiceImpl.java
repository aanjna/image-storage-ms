package com.vmware.service;

import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import com.vmware.repository.AlbumRepository;
import com.vmware.repository.ImageRepository;
import com.vmware.utills.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LogManager.getLogger(ImageServiceImpl.class);
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
    public Image findImageById(Long imgId) {
        Optional<Image> image = imageRepository.findById(imgId);
        logger.debug("log at find image by id");
        return image.get();
    }

    @Override
    public void uploadImages(MultipartFile[] images) {
        List<Image> imageList = new ArrayList<>();
        ImageAlbum album = albumRepository.getAlbumById(1L);
        for (MultipartFile imageFile : images) {
            String fileName = imageFile.getOriginalFilename();
            String randomId = UUID.randomUUID().toString();
            String randomFileName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
            String filePath = IMAGE_FOLDER + File.separator + randomFileName;

            fileFolderCheck(imageFile, filePath);

            Image imageObj = new Image();
            imageObj.setFilePath(filePath);
            imageObj.setImageAlbum(album);
            imageObj.setTitle(imageFile.getOriginalFilename());
            imageObj.setDescription("some description about image file");
            imageObj.setUploadDate(System.currentTimeMillis());
            imageList.add(imageObj);
        }
        album.setImages(imageList);

        albumRepository.save(album);
    }

    private static void fileFolderCheck(MultipartFile imageFile, String filePath) {
        File f = new File(IMAGE_FOLDER);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            Files.copy(imageFile.getInputStream(), Paths.get(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteImageBy(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image findImageByIdFromImageAlbum(Long imageId, ImageAlbum album) {
        List<Image> images = album.getImages();
        Image image = null;
        for (Image img : images) {
            if (img.getId().equals(imageId)) {
                image = img;
            }
        }
        return image;
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
