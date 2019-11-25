package com.luv2code.imageuploader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.entity.ImageFormat;
import com.luv2code.imageuploader.repository.ImageFormatRepository;
import com.luv2code.imageuploader.service.ImageFormatService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Service
public class ImageFormatServiceImpl implements ImageFormatService {

    private final ImageFormatRepository imageFormatRepository;

    @Autowired
    public ImageFormatServiceImpl(ImageFormatRepository imageFormatRepository) {
        this.imageFormatRepository = imageFormatRepository;
    }

    @Override public List<ImageFormat> findAll() {
        List<ImageFormat> imageFormats = imageFormatRepository.findAll();
        log.info("Fetching all Image formats.");
        return imageFormats;
    }
}
