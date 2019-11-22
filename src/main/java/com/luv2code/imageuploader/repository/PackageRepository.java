package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

}
