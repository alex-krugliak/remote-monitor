package com.web.service;

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

        String oneview_username = (String) token.getPrincipal();
        String oneview_password = (String) token.getCredentials();

        // EstateInfo estateInfo = checkEndCustomer(oneview_username, oneview_password);
//        EstateInfo estateInfo = authenticationStore.authenticate(oneview_username, oneview_password);
//
//        if (true) {
//            throw new BadCredentialsException("Username or password unknown");
//        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("login"));
        UsernamePasswordAuthenticationToken loggedIn = new UsernamePasswordAuthenticationToken(token.getPrincipal(), token.getCredentials(), grantedAuthorities);
//        loggedIn.setDetails(estateInfo);
        return loggedIn;
    }



    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
