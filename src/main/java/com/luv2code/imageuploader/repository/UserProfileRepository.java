package com.luv2code.imageuploader.repository;

import com.luv2code.imageuploader.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
