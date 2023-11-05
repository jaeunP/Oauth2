package com.example.oauth2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST"), USER("ROLE_USER"), ROLE("ROLE_ADMIN");

    private final String key;
}
