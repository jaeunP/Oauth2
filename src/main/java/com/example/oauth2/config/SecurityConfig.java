package com.example.oauth2.config;

import com.example.oauth2.config.auth.OAuth2SuccessHandler;
import com.example.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //인증정보를 서버에 담아두지 않는다
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oAuth2UserService);


    }
}
