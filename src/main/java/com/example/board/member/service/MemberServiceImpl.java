package com.example.board.member.service;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberEditInfo;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.MemberStatus;
import com.example.board.member.repository.MemberRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
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
                .memberStatus(MemberStatus.LOGOUT)
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

        if (member.getMemberStatus().equals(MemberStatus.LOGIN)) {
            member.setMemberStatus(MemberStatus.LOGOUT);
            memberRepository.save(member);
            return ServiceResult.fail("이미 로그인상태이므로 로그아웃합니다.");
        }
        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
            member.setMemberStatus(MemberStatus.LOGOUT);
            memberRepository.save(member);
            return ServiceResult.fail("삭제된 계정입니다.");
        }
        if (PasswordUtils.isNotEqual(memberLogin.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        String token = JwtUtils.createToken(member);
        member.setMemberStatus(MemberStatus.LOGIN);
        member.setRecentDate(LocalDateTime.now());
        memberRepository.save(member);

        return ServiceResult.success(token);
    }

    @Override
    public ServiceResult logout(String token) {
        if (Objects.equals(null, token) || token.isBlank()) {
            return ServiceResult.fail("토큰 정보가 없습니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("해당 이메일 정보가 없습니다.");
        }
        Member member = optionalMember.get();
        member.setMemberStatus(MemberStatus.LOGOUT);
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
        member.setMemberStatus(MemberStatus.DELETED);
        member.setDeleteDate(LocalDateTime.now());
        memberRepository.save(member);
        return ServiceResult.success("탈퇴성공");
    }

    @Override
    public ServiceResult refresh(String token) {
        if (Objects.equals(null, token) || token.isBlank()) {
            return ServiceResult.fail("토큰 정보가 없습니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("해당 이메일 정보가 없습니다.");
        }
        Member member = optionalMember.get();
        return ServiceResult.success(JwtUtils.createToken(member));
    }

    @Override
    public ServiceResult editPassword(MemberEditInfo memberEditInfo) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberEditInfo.getCurEmail());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("계정이 존재하지않습니다.");
        }
        Member member = optionalMember.get();
        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
            return ServiceResult.fail("삭제된 계정입니다.");
        }
        if (PasswordUtils.isNotEqual(memberEditInfo.getCurPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        member.setPassword(PasswordUtils.doEncryption(memberEditInfo.getChangedPassword()));
        memberRepository.save(member);
        return ServiceResult.success();
    }
}