package com.example.board.board.controller;

import com.example.board.board.dto.*;
import com.example.board.board.service.BoardService;
import com.example.board.common.ResponseErrors;
import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/api/post")
    public ResponseEntity<?> getAllPost(@RequestParam int page, @RequestParam int size) {
        ServiceResult result = boardService.getAllPost(page, size);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/do-posting")
    public ResponseEntity<?> doPosting(@RequestPart(name = "data") @Valid DoPostingModel doPostingModel, Errors errors, @RequestPart(name = "images", required = false) List<MultipartFile> images) throws IOException {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.doPosting(doPostingModel, images);
        return ResponseResult.result(result);
    }

    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> clickPost(@PathVariable Long postId) {
        ServiceResult result = boardService.clickPost(postId);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete/{postId}")
    public ResponseEntity<?> delete(@Valid @RequestBody MemberLogin memberLogin, Errors errors, @PathVariable Long postId) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.delete(postId, memberLogin);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-likes/{postId}")
    public ResponseEntity<?> clickLikes(@PathVariable Long postId, @RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = boardService.clickLikes(postId, token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/write-reply/{postId}")
    public ResponseEntity<?> writeReply(@RequestBody @Valid PostReplyDto postReplyDto, Errors errors, @PathVariable Long postId, @RequestHeader(name = "TOKEN") String token) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.writeReply(postId, token, postReplyDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/reply-delete/{replyId}")
    public ResponseEntity<?> deleteReply(@RequestBody @Valid DeleteReplyDto deleteReplyDto, Errors errors, @PathVariable Long replyId, @RequestHeader(name = "TOKEN") String token) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.deleteReply(replyId, token, deleteReplyDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/write-answer/{replyId}")
    public ResponseEntity<?> writeAnswer(@RequestBody @Valid WriteAnswerDto writeAnswerDto, Errors errors, @PathVariable Long replyId, @RequestHeader(name = "TOKEN") String token) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.writeAnswer(replyId, token, writeAnswerDto);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete-answer/{answerId}")
    public ResponseEntity<?> deleteAnswer(@RequestBody @Valid DeleteAnswerDto deleteAnswerDto, Errors errors, @PathVariable Long answerId, @RequestHeader(name = "TOKEN") String token) {
        List<ResponseErrors> responseErrors = new ArrayList<>();
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> responseErrors.add(ResponseErrors.of((FieldError) e)));
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.deleteAnswer(answerId, token, deleteAnswerDto);
        return ResponseResult.result(result);
    }
}
