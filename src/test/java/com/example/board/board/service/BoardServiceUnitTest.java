package com.example.board.board.service;

import com.example.board.board.dto.DeleteReplyDto;
import com.example.board.board.entity.Reply;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.MemberStatus;
import com.example.board.member.repository.MemberRepository;
import com.example.board.member.service.MemberRepositoryStub;
import com.example.board.board.dto.DoPostingModel;
import com.example.board.board.dto.PostReplyDto;
import com.example.board.board.entity.Post;
import com.example.board.board.entity.Likes;
import com.example.board.board.repository.LikesRepository;
import com.example.board.board.repository.ReplyRepository;
import com.example.board.board.repository.PostRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.board.board.enums.PostStatus.ALL;
import static com.example.board.board.enums.PostStatus.DELETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class BoardServiceUnitTest {
    private final PostRepository   postRepository   = new PostRepositoryStub();
    private final MemberRepository memberRepository = new MemberRepositoryStub();
    private final LikesRepository  likesRepository  = new LikesRepositoryStub();
    private final ReplyRepository  replyRepository  = new ReplyRepositoryStub();
    private final BoardService     boardService     = new BoardServiceImpl(postRepository, memberRepository, likesRepository, replyRepository);
    private       Member           member;
    private       Post             post;
    private       String           token;
    private       Reply            reply;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email("test1234")
                .password(PasswordUtils.doEncryption("1234"))
                .name("홍길동")
                .memberStatus(MemberStatus.LOGOUT)
                .regDate(LocalDateTime.now())
                .deleteDate(null)
                .recentDate(null)
                .build();
        memberRepository.save(member);
        token = JwtUtils.createToken(member);

        post = Post.builder()
                .postId(1L)
                .poster("test1234")
                .title("제목")
                .contents("내용")
                .postStatus(ALL)
                .hits(0)
                .likes(0)
                .postDate(LocalDateTime.now())
                .build();
        postRepository.save(post);

        reply = Reply.builder()
                .replyId(1L)
                .writer("test1234")
                .replyContents("내용")
                .postReplyStatus(ALL)
                .post(post)
                .build();
        replyRepository.save(reply);
    }

    @Test
    void doPostingSuccessTest() {
        ServiceResult result = boardService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("test1234")
                .postStatus(ALL)
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void doPostingFailByMemberTest() {
        ServiceResult result = boardService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("gjasdg1424")
                .postStatus(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void doPostingFailByMemberStatusTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail("test1234");
        optionalMember.get().setMemberStatus(MemberStatus.DELETED);
        memberRepository.save(optionalMember.get());

        ServiceResult result = boardService.doPosting(DoPostingModel.builder()
                .title("제목")
                .contents("내용")
                .poster("test1234")
                .postStatus(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 계정입니다.")
        );
    }

    @Test
    void doPostingFailByTitleTest() {
        ServiceResult result = boardService.doPosting(DoPostingModel.builder()
                .contents("내용")
                .poster("test1234")
                .postStatus(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("제목이 비었습니다.")
        );
    }

    @Test
    void doPostingFailByContentsTest() {
        ServiceResult result = boardService.doPosting(DoPostingModel.builder()
                .title("제목")
                .poster("test1234")
                .postStatus(ALL)
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("내용이 비었습니다.")
        );
    }

    @Test
    void clickPostSuccessTest() {
        ServiceResult result = boardService.clickPost(1L);
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void clickPostFailByNoPostTest() {
        ServiceResult result = boardService.clickPost(2L);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 게시글입니다.")
        );
    }

    @Test
    void clickPostFailByDeletedTest() {
        Optional<Post> optionalPost = postRepository.findById(1L);
        Post           post         = optionalPost.get();
        post.setPostStatus(DELETED);
        postRepository.save(post);

        ServiceResult result = boardService.clickPost(1L);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 게시글입니다.")
        );
    }

    @Test
    void deleteSuccessTest() {
        ServiceResult result = boardService.delete(1L, MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteFailByPostTest() {
        ServiceResult result = boardService.delete(3L, MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 게시글입니다.")
        );
    }

    @Test
    void deleteFailByDeletedPostTest() {
        Optional<Post> optionalPost = postRepository.findById(1L);
        Post           post         = optionalPost.get();
        post.setPostStatus(DELETED);
        postRepository.save(post);

        ServiceResult result = boardService.delete(1L, MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 게시글입니다.")
        );
    }

    @Test
    void deleteFailByMemberTest() {
        ServiceResult result = boardService.delete(1L, MemberLogin.builder()
                .email("test15123123")
                .password("1234")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void deleteFailByUnMatchPosterTest() {
        postRepository.save(Post.builder()
                .postId(2L)
                .poster("asdg1412")
                .title("제목")
                .contents("124")
                .likes(0)
                .hits(0)
                .postStatus(ALL)
                .build());

        ServiceResult result = boardService.delete(2L, MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("작성자가 다릅니다.")
        );
    }

    @Test
    void deleteFailByDeletedMemberTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail("test1234");
        optionalMember.get().setMemberStatus(MemberStatus.DELETED);
        memberRepository.save(optionalMember.get());

        ServiceResult result = boardService.delete(1L, MemberLogin.builder()
                .email("test1234")
                .password("1234")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 계정입니다.")
        );
    }

    @Test
    void deleteFailByPasswordTest() {
        ServiceResult result = boardService.delete(1L, MemberLogin.builder()
                .email("test1234")
                .password("54321")
                .build());
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("비밀번호가 일치하지않습니다.")
        );
    }

    @Test
    void clickLikesSuccessTest() {
        ServiceResult result = boardService.clickLikes(1L, token);
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void clickLikesFailByPostTest() {
        ServiceResult result = boardService.clickLikes(2L, token);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 게시글입니다.")
        );
    }

    @Test
    void clickLikesFailByDeletedPostTest() {
        Optional<Post> optionalPost = postRepository.findById(1L);
        Post           post         = optionalPost.get();
        post.setPostStatus(DELETED);
        postRepository.save(post);

        ServiceResult result = boardService.clickLikes(1L, token);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 게시글입니다.")
        );
    }

    @Test
    void clickLikesFailByMemberTest() {
        String newToken = JwtUtils.createToken(Member.builder()
                .email("aaaa444")
                .password("14214")
                .memberStatus(MemberStatus.LOGOUT)
                .build());

        ServiceResult result = boardService.clickLikes(1L, newToken);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void clickLikesFailByAlreadyLikesTest() {
        likesRepository.save(Likes.builder()
                .member(member)
                .post(post)
                .build());

        ServiceResult result = boardService.clickLikes(1L, token);
        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("이미 좋아요한 계정입니다.")
        );
    }

    @Test
    void writeReplySuccessTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail("test1234");
        Member           member         = optionalMember.get();
        member.setMemberStatus(MemberStatus.LOGIN);
        memberRepository.save(member);

        ServiceResult result = boardService.writeReply(1L, token, PostReplyDto.builder()
                .writer("test1234")
                .replyContents("첫댓입니다.")
                .build());

        System.out.println(result.getData());
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void writeReplyFailByPostTest() {
        ServiceResult result = boardService.writeReply(2L, token, PostReplyDto.builder()
                .writer("test1234")
                .replyContents("첫댓입니다.")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 게시글입니다.")
        );
    }

    @Test
    void writeReplyFailByDeletedPostTest() {
        Optional<Post> optionalPost = postRepository.findById(1L);
        Post           post         = optionalPost.get();
        post.setPostStatus(DELETED);
        postRepository.save(post);

        ServiceResult result = boardService.writeReply(1L, token, PostReplyDto.builder()
                .writer("test1234")
                .replyContents("첫댓입니다.")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("삭제된 게시글입니다.")
        );
    }

    @Test
    void writeReplyFailByMemberTest() {
        String newToken = JwtUtils.createToken(Member.builder()
                .email("aaaa444")
                .password("14214")
                .memberStatus(MemberStatus.LOGOUT)
                .build());

        ServiceResult result = boardService.writeReply(1L, newToken, PostReplyDto.builder()
                .writer("test1234")
                .replyContents("첫댓입니다.")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void writeReplyFailByUnmatchedMemberTest() {
        ServiceResult result = boardService.writeReply(1L, token, PostReplyDto.builder()
                .writer("test12345")
                .replyContents("첫댓입니다.")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("로그인 정보가 다릅니다.")
        );
    }

    @Test
    void deleteReplySuccessTest() {
        ServiceResult result = boardService.deleteReply(1L, token, DeleteReplyDto.builder()
                .password("1234")
                .build());

        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteReplyFailByReplyTest() {
        ServiceResult result = boardService.deleteReply(2L, token, DeleteReplyDto.builder()
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("댓글이 존재하지않습니다.")
        );
    }

    @Test
    void deleteReplyFailByMemberTest() {
        String newToken = JwtUtils.createToken(Member.builder()
                .email("aaaa444")
                .password("14214")
                .memberStatus(MemberStatus.LOGOUT)
                .build());
        ServiceResult result = boardService.deleteReply(1L, newToken, DeleteReplyDto.builder()
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("존재하지않는 계정입니다.")
        );
    }

    @Test
    void deleteReplyFailByunMatchedTest() {
        Optional<Reply> optionalReply = replyRepository.findById(1L);
        Reply           reply1        = optionalReply.get();
        reply1.setWriter("asdiwetj");
        replyRepository.save(reply1);

        ServiceResult result = boardService.deleteReply(1L, token, DeleteReplyDto.builder()
                .password("1234")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("사용자 정보가 다릅니다.")
        );
    }

    @Test
    void deleteReplyFailByPasswordTest() {
        ServiceResult result = boardService.deleteReply(1L, token, DeleteReplyDto.builder()
                .password("151515")
                .build());

        assertAll(
                () -> assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(result.getData()).isEqualTo("비밀번호가 다릅니다.")
        );
    }
}