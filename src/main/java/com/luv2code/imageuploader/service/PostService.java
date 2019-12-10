package com.luv2code.imageuploader.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;

/**
 * Created by lzugaj on Sunday, November 2019
 */

public interface PostService {

	List<Post> findAll();

	List<Post> findAllForUser(User user);

	Post save(User user, MultipartFile postImage, String postDescription, String postHashTags) throws IOException;

}
