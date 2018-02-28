package com.web.service;

import com.web.entity.Token;

/**
 * Created on 08.09.15.
 */
public interface CustomTokensService {

    Token allocateToken(String username);

    Token verifyToken(String token);

    Token isTokenExist(String username);

    void deleteUserToken();
}
