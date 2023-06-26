package com.example.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostReplyDto {
    private String     writer;
    @NotBlank(message = "내용을 입력하세요")
    private String     replyContents;
}
