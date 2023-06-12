package com.vmware;

import com.vmware.modal.ImageAlbum;
import com.vmware.repository.AlbumRepository;
import com.vmware.service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ImageAlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private AlbumService albumService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        albumService = Mockito.mock(AlbumService.class);
        albumRepository = Mockito.mock(AlbumRepository.class);
    }

//    @Test
    void testCreateAlbum() {
        String albumName = "Test Album";
        ImageAlbum expectedAlbum = new ImageAlbum();
        expectedAlbum.setName(albumName);
        when(albumRepository.save(any(ImageAlbum.class))).thenReturn(expectedAlbum);
        ImageAlbum createdAlbum = albumService.createAlbum(albumName);
        assertEquals(expectedAlbum.getName(), createdAlbum.getName());
        verify(albumRepository, times(1)).save(any(ImageAlbum.class));
    }

}
