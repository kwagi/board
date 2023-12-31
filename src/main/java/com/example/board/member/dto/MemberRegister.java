package com.example.board.member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public final class MemberRegister {
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "올바른 이메일양식을 입력하세요.")
    private String email;
    @Size(min = 4, message = "비밀번호는 4자 이상 가능합니다.")
    private String password;
    @Pattern(regexp = "^[가-힣]{2,6}$", message = "올바른 이름양식을 입력하세요.")
    private String name;
}
