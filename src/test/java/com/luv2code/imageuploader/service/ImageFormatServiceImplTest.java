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

import com.luv2code.imageuploader.entity.ImageFormat;
import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.repository.ImageFormatRepository;
import com.luv2code.imageuploader.service.impl.ImageFormatServiceImpl;

/**
 * Created by lzugaj on Monday, March 2020
 */

@SpringBootTest
public class ImageFormatServiceImplTest {

    @Mock
    private ImageFormatRepository imageFormatRepository;

    @Mock
    private PackageService packageService;

    @InjectMocks
    private ImageFormatServiceImpl imageFormatService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Long firstImageFormatId = 1L;
        ImageFormat firstImageFormat = new ImageFormat(firstImageFormatId, "JPG");

        Long secondImageFormatId = 2L;
        ImageFormat secondImageFormat = new ImageFormat(secondImageFormatId, "PNG");

        Long thirdImageFormatId = 1L;
        ImageFormat thirdImageFormat = new ImageFormat(thirdImageFormatId, "GIF");

        List<ImageFormat> imageFormats = new ArrayList<>();
        imageFormats.add(firstImageFormat);
        imageFormats.add(secondImageFormat);
        imageFormats.add(thirdImageFormat);

        when(imageFormatRepository.findAll()).thenReturn(imageFormats);

        List<ImageFormat> searchedImageFormats = imageFormatService.findAll();

        assertEquals(3, searchedImageFormats.size());
        verify(imageFormatRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllForPackage() {
        Long packageId = 1L;
        Package aPackage = new Package(packageId, "PRO", 10, 5, null, null);

        Long firstImageFormatId = 1L;
        ImageFormat firstImageFormat = new ImageFormat(firstImageFormatId, "JPG");

        Long secondImageFormatId = 2L;
        ImageFormat secondImageFormat = new ImageFormat(secondImageFormatId, "PNG");

        Long thirdImageFormatId = 1L;
        ImageFormat thirdImageFormat = new ImageFormat(thirdImageFormatId, "GIF");

        List<ImageFormat> imageFormats = new ArrayList<>();
        imageFormats.add(firstImageFormat);
        imageFormats.add(secondImageFormat);

        aPackage.setImageFormats(imageFormats);

        when(packageService.findById(aPackage.getId())).thenReturn(aPackage);
        when(imageFormatRepository.findAll()).thenReturn(imageFormats);

        List<String> searchedImageFormats = imageFormatService.findAllForPackage(aPackage.getId());

        assertEquals(2, imageFormats.size());
        assertEquals(2, searchedImageFormats.size());
    }
}
