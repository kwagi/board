package com.example.board.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberEditInfo {
    private String curEmail;
    private String curPassword;
    private String changedPassword;
}