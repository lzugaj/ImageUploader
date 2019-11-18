package com.luv2code.imageuploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luv2code.imageuploader.entity.User;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userName = ?1")
    User findByUserName(String userName);

}
