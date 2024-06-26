package com.my.movie.demo.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    SOCIAL("ROLE_SOCIAL"); //OAuth

    private final String value;
}
