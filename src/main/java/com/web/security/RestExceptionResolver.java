package com.web.security;


import com.web.wrapper.Errors;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 28.10.15.
 */
//exception Access denied
@Component("handlerExceptionResolver")
public class RestExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        if (exception instanceof AccessDeniedException) {
            try {

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ObjectMapper mapper = new ObjectMapper();
                Errors errors = new Errors();
                errors.setMessage("Access denied");
                mapper.writeValue(response.getWriter(), errors);


            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        //default processing
        return null;
    }
}