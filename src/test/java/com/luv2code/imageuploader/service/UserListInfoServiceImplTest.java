package com.luv2code.imageuploader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.CommentRepository;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.impl.UserListInfoServiceImpl;

/**
 * Created by lzugaj on Sunday, March 2020
 */

@SpringBootTest
public class UserListInfoServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private UserListInfoServiceImpl userListInfoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Long firstUserId = 1L;
        User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj@gmail.com", "lzugaj", "password1");

        Long secondUserId = 2L;
        User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma@gmail.com", "dtorma", "password2");

        Long thirdUserId = 3L;
        User thirdUser = createUser(thirdUserId, "Domagoj", "Čep", "dcep@gmail.com", "dcep", "password3");

        Long fourthUserId = 4L;
        User fourthUser = createUser(fourthUserId, "Admin", "Administrator", "admin@gmail.com", "admin", "password4");

        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);
        users.add(thirdUser);
        users.add(fourthUser);

        when(userRepository.findAll()).thenReturn(users);

        List<User> searchedUser = userListInfoService.findAll();

        assertEquals(4, users.size());
        assertEquals(3, searchedUser.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        User user = createUser(id, "Luka", "Žugaj", "lzugaj@gmail.com", "lzugaj", "password");

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        User deletedUser = userListInfoService.delete(user.getId());

        assertEquals("lzugaj", deletedUser.getUserName());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void testDeleteNullPointerException() {
        Long id = 1L;
        User user = createUser(id, "Luka", "Žugaj", "lzugaj@gmail.com", "lzugaj", "password");

        when(userRepository.findById(user.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> userListInfoService.delete(user.getId()));
    }

    private User createUser(Long id, String firstName, String lastName, String email, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserName(username);
        user.setPassword(password);
        return user;
    }
}
