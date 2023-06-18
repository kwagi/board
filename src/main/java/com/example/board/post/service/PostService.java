package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.post.dto.DoPostingModel;

public interface PostService {
    /**
     * Posting 작성
     */
    ServiceResult doPosting(DoPostingModel doPostingModel);

    ServiceResult clickPost(Long id);
}
