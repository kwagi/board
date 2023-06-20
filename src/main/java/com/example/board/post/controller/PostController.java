package com.example.board.post.controller;

import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.dto.PostReplyDto;
import com.example.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post/do-posting")
    public ResponseEntity<?> doPosting(@RequestBody DoPostingModel doPostingModel) {
        ServiceResult result = postService.doPosting(doPostingModel);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-post/{postId}")
    public ResponseEntity<?> clickPost(@PathVariable Long postId) {
        ServiceResult result = postService.clickPost(postId);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId, @RequestBody MemberLogin memberLogin) {
        ServiceResult result = postService.delete(postId, memberLogin);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-likes/{postId}")
    public ResponseEntity<?> clickLikes(@PathVariable Long postId, @RequestHeader(name = "TOKEN") String token) {
        ServiceResult result = postService.clickLikes(postId, token);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/reply/{postId}")
    public ResponseEntity<?> writeReply(@PathVariable Long postId, @RequestHeader(name = "TOKEN") String token, @RequestBody PostReplyDto postReplyDto) {
        ServiceResult result = postService.writeReply(postId, token, postReplyDto);
        return ResponseResult.result(result);
    }
}
