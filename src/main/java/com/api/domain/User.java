package com.api.domain;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonView;

@Data
public class User {
    @JsonView(value = { Views.BasicUser.class })
    private String username;
    @JsonView(value = { Views.Manager.class })
    private boolean isValid;
    @JsonView(value = { Views.Admin.class })
    private String passwordHash;
}
