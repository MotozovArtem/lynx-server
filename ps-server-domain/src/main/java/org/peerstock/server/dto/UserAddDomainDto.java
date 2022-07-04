package org.peerstock.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAddDomainDto {
    @JsonProperty("login")
    private String login;

    @JsonProperty("domain")
    private String domain;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
