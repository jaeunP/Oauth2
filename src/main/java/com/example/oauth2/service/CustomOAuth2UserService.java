package com.example.oauth2.service;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);


        // RegistrationId()
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 현재 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동시에 네이버 로그인 인지, 구글 로그인인지 구분하기 위함
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // UserNameAttributeName()
        // OAuth2 로그인 진행 시 키가 되는 필드값을 이야기 한다. Primary Key와 같은 의미.
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카 등은 기본 지원하지 않는다. 구굴의 기본 코드는 "sub"
        // 네이버 로그인과 구글 로그인을 동시에 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes
        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스.
        // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info("{}", oAuth2Attribute);

        var memberAttribute = oAuth2Attribute.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                memberAttribute, "email");
    }
}


