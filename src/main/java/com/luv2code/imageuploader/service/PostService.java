package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Sunday, November 2019
 */

public interface PostService {

	Post findById(Long id);

	List<Post> findAll();

	List<Post> findAllForUser(User user);

	Post save(User user, MultipartFile postImage, String postDescription, String postHashTags) throws IOException;

	String formatHashTags(String postHashTags);

	String getSelectedPostImage(Post post);

	Map<Long, String> mapAllPostImages(List<Post> posts);

	Map<Long, String> mapHashTags(List<Post> posts);

	Map<Long, Integer> mapDateOfAllPosts(List<Post> posts);

	Map<Long, Post> mapAllPosts(List<Post> posts);

}
