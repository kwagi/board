package com.example.board.board.service;

import com.example.board.board.dto.WriteAnswerDto;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.board.dto.DeleteReplyDto;
import com.example.board.board.dto.DoPostingModel;
import com.example.board.board.dto.PostReplyDto;

public interface BoardService {
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

    /**
     * 댓글삭제
     */
    ServiceResult deleteReply(Long replyId, String token, DeleteReplyDto deleteReplyDto);
}
