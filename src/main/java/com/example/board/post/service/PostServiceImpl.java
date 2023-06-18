package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.entity.Post;
import com.example.board.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board.post.enums.Status.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository   postRepository;
    private final MemberRepository memberRepository;

    @Override
    public ServiceResult doPosting(DoPostingModel doPostingModel) {
        Optional<Member> optionalMember = memberRepository.findByEmail(doPostingModel.getPoster());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        if (member.getStatus().equals(Status.DELETED)) {
            return ServiceResult.fail("삭제된 계정입니다.");
        }

        if (doPostingModel.getTitle() == null || doPostingModel.getTitle().isBlank()) {
            return ServiceResult.fail("제목이 비었습니다.");
        }
        if (doPostingModel.getContents() == null || doPostingModel.getContents().isBlank()) {
            return ServiceResult.fail("내용이 비었습니다.");
        }
        Post post = Post.builder()
                .title(doPostingModel.getTitle())
                .contents(doPostingModel.getContents())
                .poster(doPostingModel.getPoster())
                .status(doPostingModel.getStatus())
                .hits(0)
                .likes(0)
                .writtenDate(LocalDateTime.now())
                .build();
        postRepository.save(post);
        return ServiceResult.success(post);
    }

    @Override
    public ServiceResult clickPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }
        post.setHits(post.getHits() + 1);
        postRepository.save(post);
        return ServiceResult.success();
    }
}
