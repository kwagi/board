package com.example.board.member.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public ServiceResult register(MemberRegister memberRegister) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberRegister.getEmail());
        if (optionalMember.isPresent()) {
            return ServiceResult.fail("이미 존재하는 이메일입니다.");
        }

        Member member = Member.builder()
                .email(memberRegister.getEmail())
                .password(PasswordUtils.doEncryption(memberRegister.getPassword()))
                .name(memberRegister.getName())
                .status(Status.LOGOUT)
                .regDate(LocalDateTime.now())
                .deleteDate(null)
                .recentDate(null)
                .build();

        memberRepository.save(member);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult login(MemberLogin memberLogin) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberLogin.getEmail());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();

        if (member.getStatus().equals(Status.LOGIN)) {
            member.setStatus(Status.LOGOUT);
            memberRepository.save(member);
            return ServiceResult.fail("이미 로그인상태이므로 로그아웃합니다.");
        }
        if (member.getStatus().equals(Status.DELETED)) {
            member.setStatus(Status.LOGOUT);
            memberRepository.save(member);
            return ServiceResult.fail("삭제된 계정입니다.");
        }
        if (PasswordUtils.isNotEqual(memberLogin.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        String token = JwtUtils.createToken(member);
        member.setStatus(Status.LOGIN);
        member.setRecentDate(LocalDateTime.now());
        memberRepository.save(member);

        return ServiceResult.success(token);
    }

    @Override
    public ServiceResult logout(String token) {
        if (token == null || token.isBlank()) {
            return ServiceResult.fail("토큰 정보가 없습니다.");
        }
        String issuer;

        try {
            JwtUtils.getIssuer(token);
        } catch (SignatureVerificationException | JWTDecodeException e) {
            return ServiceResult.fail("토큰 정보가 잘못되었습니다.");
        }

        issuer = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("해당 이메일 정보가 없습니다.");
        }
        Member member = optionalMember.get();
        member.setStatus(Status.LOGOUT);
        memberRepository.save(member);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult delete(MemberDelete memberDelete) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberDelete.getEmail());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("이메일이 올바르지않습니다.");
        }
        Member member = optionalMember.get();
        if (PasswordUtils.isNotEqual(memberDelete.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        member.setStatus(Status.DELETED);
        member.setDeleteDate(LocalDateTime.now());
        memberRepository.save(member);
        return ServiceResult.success("탈퇴성공");
    }
}