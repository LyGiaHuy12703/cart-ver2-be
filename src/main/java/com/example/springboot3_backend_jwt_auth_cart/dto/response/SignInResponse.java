package com.example.springboot3_backend_jwt_auth_cart.dto.response;

import java.util.List;

public class SignInResponse {
    String access_token;
    String refresh_token;
    Long user_id;
    String username;
    String email;
    List<String> roles;

    public SignInResponse() {
    }

    public SignInResponse(String access_token,String refresh_token, Long user_id, String username,  String email, List<String> roles) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
