package com.web.service;

import com.web.entity.UsersAuthoritie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 20.04.18.
 */
@Component
public class CustomEndCustomerAuthenticator implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomEndCustomerAuthenticator.class);

    @Autowired
    private UsersService usersService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken))
            throw new RuntimeException("Expecting a UsernamePasswordAuthenticationToken");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = (String) token.getPrincipal();
        String password = (String) token.getCredentials();

        if(!usersService.checkPassword(username, password)){
            throw new BadCredentialsException("Username or password unknown");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<UsersAuthoritie> usersAuthorities = usersService.findUserAuthorities(username);
        for(UsersAuthoritie usersAuthoritie: usersAuthorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(usersAuthoritie.getAuthority()));
        }

        return new UsernamePasswordAuthenticationToken(token.getPrincipal(), "*", grantedAuthorities);

    }



    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
