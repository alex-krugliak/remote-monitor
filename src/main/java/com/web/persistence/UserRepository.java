package com.web.persistence;

import com.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created on 03.09.15.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u  where u.username = ?1 and u.password = ?2")
    User findUserByUsernameAndPassword(String user, String password);

    @Query("select u from User u  where u.username = ?1")
    User findUserByUsername(String username);
}
