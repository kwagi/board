package com.example.board.member.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberRegister;

public interface MemberService {
    /**
     * 회원가입
     */
    ServiceResult register(MemberRegister memberRegister);
}