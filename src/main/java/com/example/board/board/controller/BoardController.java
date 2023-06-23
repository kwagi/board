package com.example.board.board.controller;

import com.example.board.board.dto.*;
import com.example.board.board.service.BoardService;
import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/post/do-posting")
    public ResponseEntity<?> doPosting(@RequestBody DoPostingModel doPostingModel, List<MultipartFile> images) throws IOException {
        ServiceResult result = boardService.doPosting(doPostingModel, images);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-post/{postId}")
    public ResponseEntity<?> clickPost(@PathVariable Long postId) {
        ServiceResult result = boardService.clickPost(postId);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId, @RequestBody MemberLogin memberLogin) {
        ServiceResult result = boardService.delete(postId, memberLogin);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-likes/{postId}")
    public ResponseEntity<?> clickLikes(@PathVariable Long postId, @RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = boardService.clickLikes(postId, token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/write-reply/{postId}")
    public ResponseEntity<?> writeReply(@PathVariable Long postId, @RequestHeader(name = "TOKEN") String token, @RequestBody PostReplyDto postReplyDto) {
        ServiceResult result = boardService.writeReply(postId, token, postReplyDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/reply-delete/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId, @RequestHeader(name = "TOKEN") String token, @RequestBody DeleteReplyDto deleteReplyDto) {
        ServiceResult result = boardService.deleteReply(replyId, token, deleteReplyDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/write-answer/{replyId}")
    public ResponseEntity<?> writeAnswer(@PathVariable Long replyId, @RequestHeader(name = "TOKEN") String token, @RequestBody WriteAnswerDto writeAnswerDto) {
        ServiceResult result = boardService.writeAnswer(replyId, token, writeAnswerDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete-answer/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId, @RequestHeader(name = "TOKEN") String token, @RequestBody DeleteAnswerDto deleteAnswerDto) {
        ServiceResult result = boardService.deleteAnswer(answerId, token, deleteAnswerDto);
        return ResponseResult.result(result);
    }
}
