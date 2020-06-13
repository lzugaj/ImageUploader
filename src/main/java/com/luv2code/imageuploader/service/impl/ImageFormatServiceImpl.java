package com.luv2code.imageuploader.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.entity.ImageFormat;
import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.repository.ImageFormatRepository;
import com.luv2code.imageuploader.service.ImageFormatService;
import com.luv2code.imageuploader.service.PackageService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Service
public class ImageFormatServiceImpl implements ImageFormatService {

    private final ImageFormatRepository imageFormatRepository;

    private final PackageService packageService;

    @Autowired
    public ImageFormatServiceImpl(ImageFormatRepository imageFormatRepository, PackageService packageService) {
        this.imageFormatRepository = imageFormatRepository;
        this.packageService = packageService;
    }

    @Override
    public List<ImageFormat> findAll() {
        List<ImageFormat> imageFormats = imageFormatRepository.findAll();
        log.info("Getting all Image formats.");
        return imageFormats;
    }

    @Override
    public List<String> findAllForPackage(Long packageId) {
        Package thePackage = packageService.findById(packageId);
        List<String> imageFormats = new ArrayList<>();
        for (ImageFormat imageFormat : findAll()) {
            if (thePackage.getImageFormats().contains(imageFormat)) {
                imageFormats.add(imageFormat.getExtensionName());
            }
        }

        return imageFormats;
    }
}
