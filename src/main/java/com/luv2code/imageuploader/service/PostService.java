package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by lzugaj on Sunday, November 2019
 */

public interface PostService {

	List<Post> findAll();

	Post save(User user, MultipartFile postImage, String postDescription) throws IOException;

}
