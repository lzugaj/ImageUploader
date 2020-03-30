package com.luv2code.imageuploader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.imageuploader.entity.ImageSize;
import com.luv2code.imageuploader.repository.ImageSizeRepository;
import com.luv2code.imageuploader.service.impl.ImageSizeServiceImpl;

/**
 * Created by lzugaj on Monday, March 2020
 */

@SpringBootTest
public class ImageSizeServiceImplTest {

    @Mock
    private ImageSizeRepository imageSizeRepository;

    @InjectMocks
    private ImageSizeServiceImpl imageSizeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Long firstImageSizeId = 1L;
        ImageSize firstImageSize = new ImageSize(firstImageSizeId, 8, 8);

        Long secondSecondSizeId = 2L;
        ImageSize secondImageSize = new ImageSize(secondSecondSizeId, 16, 16);

        List<ImageSize> imageSizes = new ArrayList<>();
        imageSizes.add(firstImageSize);
        imageSizes.add(secondImageSize);

        when(imageSizeRepository.findAll()).thenReturn(imageSizes);

        List<ImageSize> searchedImageSizes = imageSizeService.findAll();

        assertEquals(2, imageSizes.size());
        assertEquals(2, searchedImageSizes.size());
        verify(imageSizeRepository, times(1)).findAll();
    }
}
