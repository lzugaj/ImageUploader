package com.luv2code.imageuploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.imageuploader.entity.Comment;

/**
 * Created by lzugaj on Thursday, December 2019
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
