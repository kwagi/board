package com.example.board.member.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
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
}