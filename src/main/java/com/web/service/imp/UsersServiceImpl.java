package com.web.service.imp;

import com.web.entity.User;
import com.web.entity.UsersAuthoritie;
import com.web.persistence.AuthoritieRepository;
import com.web.persistence.UserRepository;
import com.web.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created on 03.09.15.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthoritieRepository authoritieRepository;

    @Override
    public Boolean checkPassword(String userName, String password) {
        if (userName == null || password == null) {
            return false;
        }
        User user = userRepository.findUserByUsernameAndPassword(userName, password);
        return user != null && user.getEnabled();

    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findUserByUsername(userName);

    }

    @Override
    public List<UsersAuthoritie> findUserAuthorities(String username) {
        return authoritieRepository.findUserAuthorities(username);
    }

    public void getAsUser(String user, Set<String> authorities) {
        final SecurityContext context = SecurityContextHolder.getContext();

        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        for(String authoritie: authorities){
            grantedAuths.add(new SimpleGrantedAuthority(authoritie));
        }

        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, "", grantedAuths));

    }
}
