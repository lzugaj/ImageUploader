package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.Package;

import java.util.List;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

public interface PackageService {

	Package findOne(Long id);

	List<Package> findAll();

}
