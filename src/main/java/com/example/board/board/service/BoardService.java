package com.example.board.board.service;

import com.example.board.board.dto.*;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    /**
     * Posting 가져오기
     */
    ServiceResult getAllPost(int st, int len);

    /**
     * Posting 작성
     */
    ServiceResult doPosting(DoPostingModel doPostingModel, List<MultipartFile> images) throws IOException;

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

    /**
     * 답글쓰기
     */
    ServiceResult writeAnswer(Long replyId, String token, WriteAnswerDto writeAnswerDto);

    /**
     * 답글삭제
     */
    ServiceResult deleteAnswer(Long answerId, String token, DeleteAnswerDto deleteAnswerDto);
}
