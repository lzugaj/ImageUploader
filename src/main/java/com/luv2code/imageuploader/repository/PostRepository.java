package com.luv2code.imageuploader.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luv2code.imageuploader.entity.Post;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
