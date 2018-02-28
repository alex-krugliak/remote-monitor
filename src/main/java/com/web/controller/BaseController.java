package com.web.controller;

import com.web.exception.AuthenticationException;
import com.web.wrapper.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 02.09.15.
 */
@Controller
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Errors handleException(Exception ex) {
        Errors errors = new Errors();
        errors.setMessage("Internal server error");
        return errors;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Errors handleException(AuthenticationException ex) {
        Errors errors = new Errors();
        errors.setMessage(ex.getMessage());
        return errors;
    }


}
