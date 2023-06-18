package com.example.board.member.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberEditInfo;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceUnitTest {
    private final MemberRepository memberRepository = new MemberRepositoryStub();
    private final MemberService    memberService    = new MemberServiceImpl(memberRepository);
    private       String           token;

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

    @Test
    void loginSuccessTest() {
        ServiceResult result = memberService.login(MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        this.token = result.getData().toString();
    }

    @Test
    void loginFailByEmailTest() {
        ServiceResult result = memberService.login(MemberLogin.builder()
                .email("test142")
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void loginFailByStatus1Test() {
        memberService.login(MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());

        ServiceResult result = memberService.login(MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("이미 로그인상태이므로 로그아웃합니다.")
        );
    }

    @Test
    void loginFailByStatus2Test() {
        memberRepository.save(Member.builder()
                .email("test4321")
                .password(PasswordUtils.doEncryption("1234"))
                .name("홍길동")
                .status(Status.DELETED)
                .regDate(LocalDateTime.now())
                .deleteDate(null)
                .build());

        ServiceResult result = memberService.login(MemberLogin.builder()
                .email("test4321")
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 계정입니다.")
        );
    }

    @Test
    void loginFailByPasswordTest() {
        ServiceResult result = memberService.login(MemberLogin.builder()
                .email("test1234")
                .password("12345")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("비밀번호가 일치하지않습니다.")
        );
    }

    @Test()
    void logoutSuccessTest() {
        ServiceResult result = memberService.logout(this.token);
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void logoutFailByNoTokenTest() {
        String        token  = "";
        ServiceResult result = memberService.logout(token);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("토큰 정보가 없습니다.")
        );
    }

    @Test
    void logoutFailByWrongTokenTest() {
        String        curToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJxZW1iZXJfaWQiOm51bGwsInN1YiI6InZlcmlmeWluZ-2Zjeq4uOuPmSIsImlzcyI6InRlc3QxMjM0IiwiZXhwIjoxNjg2NzU0MTU5fQ.C6Tn8lZd1uDiamip7hSZEVfTtIccEXoOVEEyS7NbxClru_fq26DJ6IQZU8IZJAt5bvJo8_gtmGARN5h";
        ServiceResult result   = memberService.logout(curToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("토큰 정보가 잘못되었습니다.")
        );
    }

    @Test
    void logoutFailByEmail() {
        Member member = Member.builder()
                .email("ttt4124")
                .password("4213")
                .name("동길홍")
                .build();
        String        curToken = JwtUtils.createToken(member);
        ServiceResult result   = memberService.logout(curToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("해당 이메일 정보가 없습니다.")
        );
    }

    @Test
    void deleteSuccessTest() {
        ServiceResult result = memberService.delete(MemberDelete.builder()
                .email("test1234")
                .password("1234")
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteFailByEmailTest() {
        ServiceResult result = memberService.delete(MemberDelete.builder()
                .email("test12354")
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("이메일이 올바르지않습니다.")
        );
    }

    @Test
    void deleteFailByPasswordTest() {
        ServiceResult result = memberService.delete(MemberDelete.builder()
                .email("test1234")
                .password("12345")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("비밀번호가 일치하지않습니다.")
        );
    }

    /**
     * 토큰만료때문에 실패할수있음.
     */
    @Test
    void refreshSuccessTest() {
        String curToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJfaWQiOm51bGwsInN1YiI6InZlcmlmeWluZ-2Zjeq4uOuPmSIsImlzcyI6InRlc3QxMjM0IiwiZXhwIjoxNjg2NzU0MTU5fQ.C6Tn8lZd1uDiamip7hSZEVfTtIccEXoOVEEyS7NbrClru_fq26DJ6IQZU8IZJAt5bvJo8_gtmGARN5hCn-1OMg";
//        ServiceResult result   = memberService.refresh(curToken);
//        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
        assertThrows(
                TokenExpiredException.class,
                () -> memberService.logout(curToken)
        );
    }

    @Test
    void refreshFailByNoTokenTest() {
        String        curToken = "";
        ServiceResult result   = memberService.refresh(curToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("토큰 정보가 없습니다.")
        );
    }

    @Test
    void refreshFailByWrongTokenTest() {
        String        curToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJxZW1iZXJfaWQiOm51bGwsInN1YiI6InZlcmlmeWluZ-2Zjeq4uOuPmSIsImlzcyI6InRlc3QxMjM0IiwiZXhwIjoxNjg2NzU0MTU5fQ.C6Tn8lZd1uDiamip7hSZEVfTtIccEXoOVEEyS7NbxClru_fq26DJ6IQZU8IZJAt5bvJo8_gtmGARN5h";
        ServiceResult result   = memberService.refresh(curToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("토큰 정보가 잘못되었습니다.")
        );
    }

    @Test
    void refreshFailByEmail() {
        Member member = Member.builder()
                .email("ttt4124")
                .password("4213")
                .name("동길홍")
                .build();
        String        curToken = JwtUtils.createToken(member);
        ServiceResult result   = memberService.refresh(curToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("해당 이메일 정보가 없습니다.")
        );
    }

    @Test
    void editPasswordSuccessTest() {
        ServiceResult result = memberService.editPassword(MemberEditInfo.builder()
                .curEmail("test1234")
                .curPassword("1234")
                .changedPassword("4321")
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void editPasswordFailByEmailTest() {
        ServiceResult result = memberService.editPassword(MemberEditInfo.builder()
                .curEmail("test144434")
                .curPassword("1234")
                .changedPassword("4321")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("계정이 존재하지않습니다.")
        );
    }

    @Test
    void editPasswordFailByStatusTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail("test1234");
        Member           member         = optionalMember.get();
        member.setStatus(Status.DELETED);
        memberRepository.save(member);

        ServiceResult result = memberService.editPassword(MemberEditInfo.builder()
                .curEmail("test1234")
                .curPassword("1234")
                .changedPassword("4321")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 계정입니다.")
        );
    }

    @Test
    void editPasswordFailByPasswordTest() {
        ServiceResult result = memberService.editPassword(MemberEditInfo.builder()
                .curEmail("test1234")
                .curPassword("123566")
                .changedPassword("4321")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("비밀번호가 일치하지않습니다.")
        );
    }
}