package com.web.persistence;

import com.web.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created on 08.09.15.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t  where t.tokenValue = ?1")
    Token findTokenByTokenValue(String tokenValue);

    @Query("select t from Token t  where t.user.username = ?1")
    Token findTokenByUsername(String username);
}
