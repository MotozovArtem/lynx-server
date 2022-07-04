package org.peerstock.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

    @JsonProperty("token")
    private String token;

    @JsonProperty("username")
    private String username;

    public AuthToken() {

    }

    public AuthToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
