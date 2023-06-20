package com.example.board.board.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostReplyDto {
    private String writer;
    private String replyContents;
}
