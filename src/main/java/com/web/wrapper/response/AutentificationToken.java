package com.web.wrapper.response;

/**
 * Created on 29.10.15.
 */
public class AutentificationToken {

    private String token;

    private String username;

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
