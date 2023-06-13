package com.vmware;

import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import com.vmware.repository.AlbumRepository;
import com.vmware.repository.ImageRepository;
import com.vmware.service.AlbumServiceImpl;
import com.vmware.service.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageAlbumServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private ImageServiceImpl imageStorageService;

    @InjectMocks
    private AlbumServiceImpl albumService;

    private ImageAlbum imageAlbum;
    private Image image;

    @BeforeEach
    public void setUp() {
        // Create a sample ImageAlbum
        imageAlbum = new ImageAlbum();
        imageAlbum.setId(1L);
        imageAlbum.setName("imageAlbum");

        // Create a sample Image
        image = new Image();
        image.setId(1L);
        image.setTitle("Sample Image");
        image.setDescription("This is a sample image");
        image.setFilePath("/path/imageAlbum/image.jpg");
        image.setUploadDate(System.currentTimeMillis());
        image.setImageSize(new byte[]{1, 2, 3});
        image.setImageAlbum(imageAlbum);
    }

    @Test
    public void testCreateAlbum() {
        String albumName = "imageAlbum";
        when(albumRepository.save(any(ImageAlbum.class))).thenReturn(imageAlbum);
        ImageAlbum createdAlbum = albumService.createAlbum(albumName);
        assertEquals(albumName, createdAlbum.getName());
        verify(albumRepository, times(1)).save(any(ImageAlbum.class));
    }

    @Test
    public void testDeleteAlbum() {
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(imageAlbum));
        albumService.deleteAlbumById(albumId);
        verify(albumRepository, times(1)).findById(albumId);
        verify(albumRepository, times(1)).delete(any(ImageAlbum.class));
    }

    @Test
    public void testGetImageByIdFromAlbum() {
        Long imageId = 1L;
        when(imageRepository.findByIdAndAlbumId(imageId, imageAlbum.getId())).thenReturn(Optional.of(image));
        Image foundImage = imageStorageService.findImageByIdFromImageAlbum(imageId, imageAlbum);
        assertEquals(image.getTitle(), foundImage.getTitle());
        assertEquals(image.getDescription(), foundImage.getDescription());
        assertEquals(image.getFilePath(), foundImage.getFilePath());
        assertEquals(image.getUploadDate(), foundImage.getUploadDate());
        assertEquals(image.getImageAlbum(), foundImage.getImageAlbum());
        assertEquals(image.getImageSize(), foundImage.getImageSize());
        verify(imageRepository, times(1)).findByIdAndAlbumId(imageId, imageAlbum.getId());
    }

    @Test
    public void testGetAllImages() {
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(imageAlbum));
        List<Image> retrievedImages = imageStorageService.findByAlbumId(imageAlbum.getId());
        assertEquals(1, retrievedImages.size());
        assertEquals(image, retrievedImages.get(0));
        verify(imageRepository, times(1)).findById(imageAlbum.getId());
    }

    @Test
    public void testDeleteImage() {
        Long imageId = 1L;
        when(imageRepository.findByIdAndAlbumId(imageId, imageAlbum.getId())).thenReturn(Optional.of(image));
        imageStorageService.deleteImage(imageId, imageAlbum.getId());
        verify(imageRepository, times(1)).findByIdAndAlbumId(imageId, imageAlbum.getId());
        verify(imageRepository, times(1)).delete(any(Image.class));
    }

    @Test
    public void testUploadImage() throws Exception {
        Long albumId = 1L;
        MultipartFile imageFile = new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(imageAlbum));
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        Image uploadedImage = imageStorageService.uploadImage(albumId, imageFile);
        assertEquals(image.getTitle(), uploadedImage.getTitle());
        assertEquals(image.getDescription(), uploadedImage.getDescription());
        assertEquals(image.getFilePath(), uploadedImage.getFilePath());
        assertEquals(image.getUploadDate(), uploadedImage.getUploadDate());
        assertEquals(image.getImageAlbum(), uploadedImage.getImageAlbum());
        assertEquals(image.getImageSize(), uploadedImage.getImageSize());
        verify(albumRepository, times(1)).findById(albumId);
        verify(imageRepository, times(1)).save(any(Image.class));
    }
}


