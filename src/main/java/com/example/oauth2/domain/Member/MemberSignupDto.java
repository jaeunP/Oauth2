package com.example.oauth2.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignupDto {

    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}
