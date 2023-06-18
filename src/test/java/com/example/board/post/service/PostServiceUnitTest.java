package com.example.board.post.service;

import com.example.board.common.ServiceResult;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.Status;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberRepositoryStub;
import com.example.board.post.dto.DoPostingModel;
import com.example.board.post.repository.PostRepository;
import com.example.board.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board.post.enums.Status.ALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostServiceUnitTest {
    private final PostRepository   postRepository   = new PostRepositoryStub();
    private final MemberRepository memberRepository = new MemberRepositoryStub();
    private final PostService      postService      = new PostServiceImpl(postRepository, memberRepository);

    @BeforeEach
    void setUp() {
        // 회원가입시 초기상태
        memberRepository.save(Member.builder()
                .email("test1234")
                .password(PasswordUtils.doEncryption("1234"))
                .name("홍길동")
                .status(Status.LOGOUT)
                .regDate(LocalDateTime.now())
                .deleteDate(null)
                .recentDate(null)
                .build());
    }

    @Test
    void doPostingSuccessTest() {
        ServiceResult result = postService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("test1234")
                .status(ALL)
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void doPostingFailByMemberTest() {
        ServiceResult result = postService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("gjasdg1424")
                .status(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void doPostingFailByMemberStatusTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail("test1234");
        optionalMember.get().setStatus(Status.DELETED);
        memberRepository.save(optionalMember.get());

        ServiceResult result = postService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("test1234")
                .status(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 계정입니다.")
        );
    }

    @Test
    void doPostingFailByTitleTest() {
        ServiceResult result = postService.doPosting(DoPostingModel.builder()
                .contents("내용")
                .poster("test1234")
                .status(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("제목이 비었습니다.")
        );
    }

    @Test
    void doPostingFailByContentsTest() {
        ServiceResult result = postService.doPosting(DoPostingModel.builder()
                .title("제목")
                .poster("test1234")
                .status(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("내용이 비었습니다.")
        );
    }
}