package com.luv2code.imageuploader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.entity.ImageSize;
import com.luv2code.imageuploader.repository.ImageSizeRepository;
import com.luv2code.imageuploader.service.ImageSizeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Service
public class ImageSizeServiceImpl implements ImageSizeService {

    private final ImageSizeRepository imageSizeRepository;

    @Autowired
    public ImageSizeServiceImpl(ImageSizeRepository imageSizeRepository) {
        this.imageSizeRepository = imageSizeRepository;
    }

    @Override public List<ImageSize> findAll() {
        List<ImageSize> imageSizes = imageSizeRepository.findAll();
        log.info("Fetching all Image sizes.");
        return imageSizes;
    }
}
