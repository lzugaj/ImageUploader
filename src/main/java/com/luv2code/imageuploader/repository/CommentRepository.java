package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lzugaj on Thursday, December 2019
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByUserId(Long userId);

}
