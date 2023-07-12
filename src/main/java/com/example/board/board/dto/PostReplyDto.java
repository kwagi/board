package com.example.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostReplyDto {
    @NotBlank(message = "작성자가 없습니다.")
    private String writer;
    @NotBlank(message = "내용을 입력하세요")
    private String replyContents;
}
