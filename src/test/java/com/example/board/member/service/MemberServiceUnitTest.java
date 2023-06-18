package com.example.board.member.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
import com.example.board.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberServiceUnitTest {
    private final MemberRepository memberRepository = new MemberRepositoryStub();
    private final MemberService    memberService    = new MemberServiceImpl(memberRepository);

    @BeforeEach
    void setUp() {
        // 회원가입시 초기상태
        memberRepository.save(Member.builder()
                .email("test1234")
                .password(PasswordUtils.doEncryption("1234"))
                .name("홍길동")
                .status(Status.LOGOUT)
                .regDate(LocalDateTime.now())
                .deleteDate(null)
                .recentDate(null)
                .build());
    }

    @Test
    void registerSuccessTest() {
        ServiceResult result = memberService.register(MemberRegister.builder()
                .email("test4321")
                .password("235235")
                .name("홍동길")
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void registerFailByEmailTest() {
        ServiceResult result = memberService.register(MemberRegister.builder()
                .email("test1234")
                .password("4241")
                .name("홍길동동")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("이미 존재하는 이메일입니다.")
        );
    }
}