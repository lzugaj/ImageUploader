package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;

/**
 * Created by lzugaj on Sunday, November 2019
 */

public interface PostService {

	Post save(Post post, User user);

}
