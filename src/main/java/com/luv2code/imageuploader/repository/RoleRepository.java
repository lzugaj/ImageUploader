package com.luv2code.imageuploader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.luv2code.imageuploader.entity.Role;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name = ?1")
    Role findRoleByName(String roleName);

}
