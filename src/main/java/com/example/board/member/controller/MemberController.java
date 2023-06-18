package com.example.board.member.controller;

import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberEditInfo;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public final class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/member/register")
    public ResponseEntity<?> register(@RequestBody MemberRegister memberRegister) {
        ServiceResult result = memberService.register(memberRegister);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/login")
    public ResponseEntity<?> login(@RequestBody MemberLogin memberLogin) {
        ServiceResult result = memberService.login(memberLogin);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = memberService.logout(token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/delete")
    public ResponseEntity<?> delete(@RequestBody MemberDelete memberDelete) {
        ServiceResult result = memberService.delete(memberDelete);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/refresh")
    public ResponseEntity<?> delete(@RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = memberService.refresh(token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/edit-password")
    public ResponseEntity<?> editPassword(@RequestBody MemberEditInfo memberEditInfo) {
        ServiceResult result = memberService.editPassword(memberEditInfo);
        return ResponseResult.result(result);
    }
}