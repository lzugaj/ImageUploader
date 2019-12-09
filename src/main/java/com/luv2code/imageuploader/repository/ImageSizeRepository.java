package com.luv2code.imageuploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.imageuploader.entity.ImageSize;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Repository
public interface ImageSizeRepository extends JpaRepository<ImageSize, Long> {

}
