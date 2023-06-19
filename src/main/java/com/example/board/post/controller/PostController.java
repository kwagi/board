package com.example.board.post.controller;

import com.example.board.common.ResponseResult;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post/do-posting")
    public ResponseEntity<?> doPosting(@RequestBody DoPostingModel doPostingModel) {
        ServiceResult result = postService.doPosting(doPostingModel);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/click-post/{id}")
    public ResponseEntity<?> clickPost(@PathVariable Long id) {
        ServiceResult result = postService.clickPost(id);
        return ResponseResult.result(result);
    }

    @PostMapping("/api/post/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody MemberLogin memberLogin) {
        ServiceResult result = postService.delete(id, memberLogin);
        return ResponseResult.result(result);
    }
}
