package com.example.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoPostingModel {
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotBlank(message = "내용을 입력하세요")
    private String contents;
    private String poster;
}
