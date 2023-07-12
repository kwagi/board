package com.example.board.member.controller;

import com.example.board.common.ResponseErrors;
import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberDelete;
import com.example.board.member.dto.MemberEditInfo;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.dto.MemberRegister;
import com.example.board.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public final class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/member/register")
    public ResponseEntity<?> register(@RequestBody @Valid MemberRegister memberRegister, Errors errors) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = memberService.register(memberRegister);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid MemberLogin memberLogin, Errors errors) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = memberService.login(memberLogin);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "TOKEN") @Valid String token) {
        ServiceResult result = memberService.logout(token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid MemberDelete memberDelete, Errors errors) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = memberService.delete(memberDelete);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/refresh")
    public ResponseEntity<?> delete(@RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = memberService.refresh(token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/member/edit-password")
    public ResponseEntity<?> editPassword(@RequestBody @Valid MemberEditInfo memberEditInfo, Errors errors) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = memberService.editPassword(memberEditInfo);
        return ResponseResult.result(result);
    }
}