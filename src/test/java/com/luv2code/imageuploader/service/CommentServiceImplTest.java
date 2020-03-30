package com.luv2code.imageuploader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.imageuploader.entity.Comment;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.CommentRepository;
import com.luv2code.imageuploader.service.impl.CommentServiceImpl;

;

/**
 * Created by lzugaj on Monday, March 2020
 */

@SpringBootTest
public class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        Long userId = 1L;
        User user = new User(userId, "Luka", "Žugaj", "lzugaj@gmail.com", "lzugaj", "#Lzugaj1234", 0L, 0, null, null, null, null, null, null);

        Long postId = 1L;
        Post post = new Post(postId, "Description", "hash tag", null, LocalDateTime.now(), 0, 0, 0L, null, null, null);

        Long commentId = 1L;
        Comment comment = new Comment(commentId, "Desc", 0, 0, LocalDateTime.now(), user, post);

        when(userService.findByUserName(user.getUserName())).thenReturn(user);
        when(postService.findById(post.getId())).thenReturn(post);
        when(commentRepository.save(comment)).thenReturn(comment);

        String description = "Description";
        String username = "lzugaj";
        Long id = 1L;
        Comment newComment = commentService.save(description, username, id);

        assertNotNull(newComment);
        assertEquals("Description", newComment.getDescription());
        assertEquals(0, newComment.getNumberOfLikes());
        assertEquals(0, newComment.getNumberOfDislikes());
        assertEquals("lzugaj", newComment.getUser().getUserName());
    }

    @Test
    public void testFindAll() {
        Long firstPostId = 1L;
        Post firstPost = new Post(firstPostId, "Description", "hash tag", null, LocalDateTime.now(), 0, 0, 0L, null, null, null);

        Long secondPostId = 2L;
        Post secondPost = new Post(secondPostId, "Opis", "hash tag22222", null, LocalDateTime.now(), 0, 0, 0L, null, null, null);

        Long firstCommentId = 1L;
        Comment firstComment = createComment(firstCommentId, "Opis 1");

        Long secondCommentId = 2L;
        Comment secondComment = createComment(secondCommentId, "Opis 2");

        Long thirdCommentId = 3L;
        Comment thirdComment = createComment(thirdCommentId, "Opis 3");

        firstComment.setPost(firstPost);
        secondComment.setPost(firstPost);
        thirdComment.setPost(secondPost);

        List<Comment> comments = new ArrayList<>();
        comments.add(firstComment);
        comments.add(secondComment);
        comments.add(thirdComment);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> searchedComments = commentService.findAll(firstPost.getId());

        assertEquals(3, comments.size());
        assertEquals(2, searchedComments.size());
    }

    @Test
    public void testNumberOfCommentsForPost() {
        Long firstPostId = 1L;
        Post firstPost = new Post(firstPostId, "Description", "hash tag", null, LocalDateTime.now(), 0, 0, 0L, null, null, null);

        Long secondPostId = 2L;
        Post secondPost = new Post(secondPostId, "Opis", "hash tag22222", null, LocalDateTime.now(), 0, 0, 0L, null, null, null);

        Long firstCommentId = 1L;
        Comment firstComment = createComment(firstCommentId, "Opis 1");

        Long secondCommentId = 2L;
        Comment secondComment = createComment(secondCommentId, "Opis 2");

        Long thirdCommentId = 3L;
        Comment thirdComment = createComment(thirdCommentId, "Opis 3");

        firstComment.setPost(firstPost);
        secondComment.setPost(firstPost);
        thirdComment.setPost(secondPost);

        List<Comment> comments = new ArrayList<>();
        comments.add(firstComment);
        comments.add(secondComment);

        when(commentService.findAll(firstPost.getId())).thenReturn(comments);

        int numberOfCommentForPost = commentService.numberOfCommentsForPost(firstPost.getId());

        assertEquals(2, comments.size());
        assertEquals(2, numberOfCommentForPost);
    }

    private Comment createComment(Long id, String description) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setDescription(description);
        comment.setDateOfPost(LocalDateTime.now());
        comment.setNumberOfLikes(0);
        comment.setNumberOfDislikes(2);
        return comment;
    }
}
