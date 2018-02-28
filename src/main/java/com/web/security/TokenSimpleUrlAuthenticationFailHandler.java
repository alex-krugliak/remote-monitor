package com.web.security;

import com.web.wrapper.Errors;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.map.util.JSONWrappedObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 28.10.15.
 */
public class TokenSimpleUrlAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger = Logger.getLogger(TokenSimpleUrlAuthenticationFailHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        Errors errors = new Errors();
        errors.setMessage("Valid token not found");

        try {
            mapper.writeValue(response.getWriter(), errors);

        } catch (Exception e) {
            throw new IOException();
        }
    }
}
