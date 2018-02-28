package com.web.security.filter;

import com.web.entity.Token;
import com.web.entity.User;
import com.web.entity.UsersAuthoritie;
import com.web.service.CustomTokensService;
import com.web.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by on 06.01.15.
 */

public class UserAuthenticationFilterCustom extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = Logger.getLogger(UserAuthenticationFilterCustom.class);

    @Autowired
    private CustomTokensService tokensService;

    @Autowired
    private UsersService usersService;

    private boolean postOnly = true;
    private static final String usernameParameter = "j_username";
    private static final String passwordParameter = "j_password";


    public UserAuthenticationFilterCustom() {
        //super("/j_spring_security_check");
        super(AnyRequestMatcher.INSTANCE);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String requestToken = request.getHeader("Auth-Token");
        if (requestToken == null) {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("anonymous", "");
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

        Token token = tokensService.verifyToken(requestToken);
        if (token == null || token.getUser().getUsername() == null) {
            throw new AuthenticationServiceException("Authentication error");
        }

        User user = token.getUser();

        List<UsersAuthoritie> userAuthorities = usersService.findUserAuthorities(user.getUsername());

        Set<GrantedAuthority> roles = new HashSet<>();
        for (UsersAuthoritie userAuthority : userAuthorities) {
            roles.add(new SimpleGrantedAuthority(userAuthority.getAuthority()));
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), roles);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


}
