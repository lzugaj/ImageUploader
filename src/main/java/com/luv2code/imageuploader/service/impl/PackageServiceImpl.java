package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.repository.PackageRepository;
import com.luv2code.imageuploader.service.PackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Slf4j
@Service
public class PackageServiceImpl implements PackageService {

	private final PackageRepository packageRepository;

	public PackageServiceImpl(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	@Override
	public Package findById(Long id) {
		Package searchedPackage = packageRepository.findById(id).orElse(null);
		log.info("Searching Package with id: `{}`.", id);
		return searchedPackage;
	}

	@Override
	public List<Package> findAll() {
		List<Package> packages = packageRepository.findAll();
		log.info("Getting all Packages.");
		return packages;
	}
}
