package com.luv2code.imageuploader.service;

import java.util.List;

import com.luv2code.imageuploader.entity.Package;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

public interface PackageService {

	Package findOne(Long id);

	List<Package> findAll();

}
