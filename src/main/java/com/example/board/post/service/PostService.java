package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.dto.PostReplyDto;

public interface PostService {
    /**
     * Posting 작성
     */
    ServiceResult doPosting(DoPostingModel doPostingModel);

    /**
     * 게시글 보기
     */
    ServiceResult clickPost(Long postId);

    /**
     * 게시글 삭제
     */
    ServiceResult delete(Long postId, MemberLogin memberLogin);

    /**
     * 좋아요 누르기
     */
    ServiceResult clickLikes(Long postId, String token);

    /**
     * 댓글쓰기
     */
    ServiceResult writeReply(Long postId, String token, PostReplyDto postReplyDto);
}
