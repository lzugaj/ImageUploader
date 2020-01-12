package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
