package com.vmware;

import com.vmware.modal.Image;
import com.vmware.modal.ImageAlbum;
import com.vmware.repository.ImageRepository;
import com.vmware.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ImageStorageApplicationTests {

    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        imageService = Mockito.mock(ImageService.class);
        imageRepository = Mockito.mock(ImageRepository.class);
    }

    @Test
    void test_FindImageById() {
        Long imgId = 1L;
        Image expectedImage = new Image();
        expectedImage.setId(imgId);
        expectedImage.setTitle("Test Title");
        when(imageRepository.findById(imgId)).thenReturn(Optional.of(expectedImage));
        Image actualImage = imageService.findImageById(imgId);
        assertEquals(expectedImage.getId(), actualImage.getId());
        assertEquals(expectedImage.getTitle(), actualImage.getTitle());
        verify(imageRepository, times(1)).findById(imgId);
    }

    @Test
    void test_getAllImages_inAlbum() {
        ImageAlbum album = new ImageAlbum();
        album.setId(1l);
        Image img = new Image();
        img.setId(1l);
        img.setImageSize(new byte[1]);
        img.setTitle("Image Title");
        img.setDescription("Img Description");
        img.setUploadDate(System.currentTimeMillis());
        img.setImageAlbum(album);
        when(imageRepository.findById(img.getId())).thenReturn(Optional.of(img));
        List<Image> imgList = new ArrayList<>();
        imgList.add(img);
        assertEquals(1l, imgList.get(0).getId());
    }

    @Test
    void test_DeleteImage() {
        Long imgId = 1L;
        imageService.deleteImageById(imgId);
        verify(imageRepository, times(1)).deleteById(imgId);
    }

}
