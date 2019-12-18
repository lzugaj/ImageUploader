package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.userName = ?1")
//    User findByUsername(String userName);

    User findByUserName(String userName);

}
