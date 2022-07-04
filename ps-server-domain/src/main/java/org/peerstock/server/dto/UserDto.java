package org.peerstock.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("domain")
    private List<String> domain;

    @JsonProperty("online")
    private Boolean online;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDomain() {
        return domain;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
