package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Post findById(Long id) {
		Post post = postRepository.findById(id).orElse(null);
		log.info("Getting Post with id: `{}`", id);
		return post;
	}

	@Override
	public List<Post> findAll() {
		List<Post> posts = postRepository.findAll();
		posts.sort(Collections.reverseOrder());
		log.info("Getting all Posts.");
		return posts;
	}

	@Override
	public List<Post> findAllForUser(User user) {
		List<Post> posts = findAll();
		List<Post> userPosts = new ArrayList<>();
		log.info("Getting all Posts for User with username: `{}`", user.getUserName());
		for (Post post : posts) {
			if (post.getUser().getUserName().equals(user.getUserName())) {
				userPosts.add(post);
			}
		}

		return userPosts;
	}

	@Override
	public Post save(User user, MultipartFile postImage, String postDescription, String postHashTags) throws IOException {
		User postCreator = userRepository.findById(user.getId()).orElse(null);
		log.info("Getting User with id: `{}`.", user.getId());
		String imageFileName = StringUtils.cleanPath(postImage.getOriginalFilename());
		log.info("Successfully get image file name: `{}`.", imageFileName);

		Post newPost = null;
		if (postCreator != null) {
			newPost = setVariablesBeforeSave(postCreator, postImage, postDescription, postHashTags);
			log.info("Setting Post attributes.");
		}

		newPost = postRepository.save(newPost);
		log.info("Saving new Post with id: `{}`.", newPost.getId());
		return newPost;
	}

	private Post setVariablesBeforeSave(User postCreator, MultipartFile postImage, String postDescription, String postHashTags) throws IOException {
		Post newPost = new Post();
		String generateHashTagsFormat = generateHastTagsFormat(postHashTags);

		newPost.setUser(postCreator);
		newPost.setDescription(postDescription);
		newPost.setPostImage(postImage.getBytes());
		newPost.setHashTag(generateHashTagsFormat);
		newPost.setNumberOfLikes(0);
		newPost.setNumberOfDownloads(0);
		newPost.setImageFileSize(postImage.getSize());
		newPost.setDateOfPost(LocalDateTime.now());
		newPost.setComments(null);
		newPost.setDownloadImages(null);

		postCreator.setUploadedImageSizeWithCurrentPackage(postCreator.getUploadedImageSizeWithCurrentPackage() + postImage.getSize());
		postCreator.setUploadedImagesWithCurrentPackage(postCreator.getUploadedImagesWithCurrentPackage() + 1);

		return newPost;
	}

	private String generateHastTagsFormat(String postHashTags) {
		if (postHashTags != null) {
			String[] hashTags = postHashTags.split("\\s+");
			List<String> newHashTags = new ArrayList<>();
			for (String hashTag : hashTags) {
				hashTag = "#" + hashTag;
				newHashTags.add(hashTag);
			}

			return String.valueOf(newHashTags);
		} else {
			return "";
		}
	}

	@Override
	public String formatHashTags(String postHashTags) {
		return postHashTags.replace("[", "").replace("]", "").replace(',', ' ').trim();
	}

	@Override
	public String getSelectedPostImage(Post post) {
		byte[] postImage = Base64.getEncoder().encode(post.getPostImage());
		log.info("Getting post image.");

		String imageUrl = new String(postImage, StandardCharsets.UTF_8);
		log.info("Successfully founded image url: ``{}", imageUrl);

		return imageUrl;
	}

	@Override
	public Map<Long, String> mapAllPostImages(List<Post> posts) {
		Map<Long, String> postImages = new HashMap<>();
		for (Post post : posts) {
            byte[] postImage = Base64.getEncoder().encode(post.getPostImage());
            String imageUrl = new String(postImage, StandardCharsets.UTF_8);
            postImages.put(post.getId(), imageUrl);
        }

		return postImages;
	}

	@Override
	public Map<Long, String> mapHashTags(List<Post> posts) {
		Map<Long, String> hashTags = new HashMap<>();
		for (Post post : posts) {
			String hashTag = formatHashTags(post.getHashTag());
			hashTags.put(post.getId(), hashTag);
		}

		return hashTags;
	}

	@Override
	public Map<Long, Integer> mapDateOfAllPosts(List<Post> posts) {
		Map<Long, Integer> daysOfMonth = new HashMap<>();
		for (Post post : posts) {
			daysOfMonth.put(post.getId(), post.getDateOfPost().getDayOfMonth());
		}

		return daysOfMonth;
	}

	@Override
	public List<Post> filterPostsByMandatoryCriteria(String hashTag, Double sizeFrom, Date dateFrom, String author) {
		List<Post> posts = findAll();
		List<Post> foundedPosts = new ArrayList<>();
		for (Post post : posts) {
			if (post.getHashTag().contains(hashTag) &&
					Double.valueOf(post.getImageFileSize()) >= sizeFrom &&
					post.getDateOfPost().isAfter(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getUser().getUserName().contains(author)) {
				foundedPosts.add(post);
			}
		}

		return foundedPosts;
	}

	@Override
	public List<Post> filterPostsByMandatoryAndSizeToCriteria(String hashTag, Double sizeFrom, Double sizeTo, Date dateFrom, String author) {
		List<Post> posts = findAll();
		List<Post> foundedPosts = new ArrayList<>();
		for (Post post : posts) {
			if (post.getHashTag().contains(hashTag) &&
					Double.valueOf(post.getImageFileSize()) >= sizeFrom &&
					Double.valueOf(post.getImageFileSize()) <= sizeTo &&
					post.getDateOfPost().isAfter(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getUser().getUserName().contains(author)) {
				foundedPosts.add(post);
			}
		}

		return foundedPosts;
	}

	@Override
	public List<Post> filterPostsByMandatoryAndDateToCriteria(String hashTag, Double sizeFrom, Date dateFrom, Date dateTo, String author) {
		List<Post> posts = findAll();
		List<Post> foundedPosts = new ArrayList<>();
		for (Post post : posts) {
			if (post.getHashTag().contains(hashTag) &&
					Double.valueOf(post.getImageFileSize()) >= sizeFrom &&
					post.getDateOfPost().isAfter(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getDateOfPost().isBefore(dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getUser().getUserName().contains(author)) {
				foundedPosts.add(post);
			}
		}

		return foundedPosts;
	}

	@Override
	public List<Post> filterPostsByAllCriteria(String hashTag, Double sizeFrom, Double sizeTo, Date dateFrom, Date dateTo, String author) {
		List<Post> posts = findAll();
		List<Post> foundedPosts = new ArrayList<>();
		for (Post post : posts) {
			if (post.getHashTag().contains(hashTag) &&
					Double.valueOf(post.getImageFileSize()) >= sizeFrom &&
					Double.valueOf(post.getImageFileSize()) <= sizeTo &&
					post.getDateOfPost().isAfter(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getDateOfPost().isBefore(dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) &&
					post.getUser().getUserName().contains(author)) {
				foundedPosts.add(post);
			}
		}

		return foundedPosts;
	}

	public Map<Long, Post> mapAllPosts(List<Post> posts) {
		Map<Long, Post> postsMap = new HashMap<>();
		for (Post post : posts) {
			postsMap.put(post.getId(), post);
		}

		return postsMap;
	}
}
