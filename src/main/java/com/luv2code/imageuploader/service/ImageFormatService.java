package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.ImageFormat;

import java.util.List;

/**
 * Created by lzugaj on Monday, November 2019
 */

public interface ImageFormatService {

    List<ImageFormat> findAll();

    List<String> findAllForPackage(Long packageId);

}
