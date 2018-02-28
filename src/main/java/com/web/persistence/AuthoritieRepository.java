package com.web.persistence;

import com.web.entity.UsersAuthoritie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created on 03.09.15.
 */
public interface AuthoritieRepository extends JpaRepository<UsersAuthoritie, Long> {

    @Query("select a from UsersAuthoritie a  where a.user.username = ?1")
    List<UsersAuthoritie> findUserAuthorities(String username);

}
