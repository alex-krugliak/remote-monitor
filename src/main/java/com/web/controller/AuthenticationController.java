package com.web.controller;

import com.web.entity.Token;
import com.web.exception.ApplicationException;
import com.web.exception.AuthenticationException;
import com.web.service.CustomTokensService;
import com.web.service.UsersService;
import com.web.wrapper.request.AutentificationRequestWrapper;
import com.web.wrapper.response.AutentificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

/**
 * Created on 01.09.15.
 */

@Controller
public class AuthenticationController extends BaseController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private CustomTokensService customTokensService;

    @RequestMapping("/login")
    public ModelAndView login(ServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/views/login.html");
        modelAndView.addObject("error", "dddddd");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/token")
    public AutentificationToken loginPage(@RequestBody AutentificationRequestWrapper autWrapper) throws ApplicationException {

        if (!usersService.checkPassword(autWrapper.getLogin(), autWrapper.getPassword())) {
            throw new AuthenticationException("Wrong username or password");
        }

        Token token = customTokensService.allocateToken(autWrapper.getLogin());


        AutentificationToken responseToken = new AutentificationToken();
        responseToken.setToken(token.getTokenValue());
        responseToken.setUserName(token.getUser().getUsername());

        return responseToken;
    }

    @ResponseBody
    @RequestMapping("/logout")
    public void logout() throws ApplicationException {
        customTokensService.deleteUserToken();
    }
}
