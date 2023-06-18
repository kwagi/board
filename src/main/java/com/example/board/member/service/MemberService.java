package com.example.board.member.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;

public interface MemberService {
    /**
     * 회원가입
     */
    ServiceResult register(MemberRegister memberRegister);

    /**
     * 로그인
     */
    ServiceResult login(MemberLogin memberLogin);

    /**
     * 로그아웃
     */
    ServiceResult logout(String token);

    /**
     * 회원탈퇴
     */
    ServiceResult delete(MemberDelete memberDelete);
}