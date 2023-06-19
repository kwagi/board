package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.MemberStatus;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.entity.Post;
import com.example.board.post.entity.PostLikes;
import com.example.board.post.repository.PostLikesRepository;
import com.example.board.post.repository.PostRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board.post.enums.PostStatus.DELETED;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository      postRepository;
    private final MemberRepository    memberRepository;
    private final PostLikesRepository postLikesRepository;

    @Override
    public ServiceResult doPosting(DoPostingModel doPostingModel) {
        Optional<Member> optionalMember = memberRepository.findByEmail(doPostingModel.getPoster());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
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
                .postStatus(doPostingModel.getPostStatus())
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
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }
        post.setHits(post.getHits() + 1);
        postRepository.save(post);
        return ServiceResult.success(post);
    }

    @Override
    public ServiceResult delete(Long id, MemberLogin memberLogin) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(memberLogin.getEmail());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();

        if (!memberLogin.getEmail().equals(post.getPoster())) {
            return ServiceResult.fail("작성자가 다릅니다.");
        }
        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
            return ServiceResult.fail("삭제된 계정입니다.");
        }
        if (PasswordUtils.isNotEqual(memberLogin.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        post.setPostStatus(DELETED);
        postRepository.save(post);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult clickLikes(Long id, String token) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        long   count  = postLikesRepository.countByPostLikesIdAndMember(id, member);
        if (count > 0) {
            return ServiceResult.fail("이미 좋아요한 계정입니다.");
        }

        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);

        PostLikes postLikes = PostLikes.builder()
                .member(member)
                .post(post)
                .build();
        postLikesRepository.save(postLikes);
        return ServiceResult.success();
    }
}
