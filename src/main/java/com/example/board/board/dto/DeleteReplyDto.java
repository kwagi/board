package com.example.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DeleteReplyDto {
    @NotBlank(message = "비밀번호는 공백일수없습니다.")
    @Size(min = 4, message = "비밀번호는 4자 이상 가능합니다.")
    private String password;
}
