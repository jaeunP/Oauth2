package com.example.oauth2.service;

import com.example.oauth2.domain.Member.Member;
import com.example.oauth2.domain.Member.MemberSignupDto;
import com.example.oauth2.domain.Role;
import com.example.oauth2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(MemberSignupDto memberSignupDto) throws Exception {

        if(memberRepository.findByEmail(memberSignupDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }
        if (memberRepository.findByNickname(memberSignupDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        Member member = Member.builder()
                .email(memberSignupDto.getEmail())
                .password(memberSignupDto.getPassword())
                .nickname(memberSignupDto.getNickname())
                .age(memberSignupDto.getAge())
                .city(memberSignupDto.getCity())
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }
}
