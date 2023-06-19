package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.post.dto.DoPostingModel;

public interface PostService {
    /**
     * Posting 작성
     */
    ServiceResult doPosting(DoPostingModel doPostingModel);

    /**
     * 게시글 보기
     */
    ServiceResult clickPost(Long id);

    /**
     * 게시글 삭제
     */
    ServiceResult delete(Long id, MemberLogin memberLogin);
}
