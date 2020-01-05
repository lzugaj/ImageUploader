package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
