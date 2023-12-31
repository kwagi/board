package com.example.board.board.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.board.board.dto.*;
import com.example.board.board.entity.*;
import com.example.board.board.repository.*;
import com.example.board.common.ServiceResult;
import com.example.board.member.dto.MemberLogin;
import com.example.board.member.entity.Member;
import com.example.board.member.enums.MemberStatus;
import com.example.board.member.repository.MemberRepository;
import com.example.board.util.JwtUtils;
import com.example.board.util.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.board.board.enums.PostStatus.ALL;
import static com.example.board.board.enums.PostStatus.DELETED;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final PostRepository   postRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository  likesRepository;
    private final ReplyRepository  replyRepository;
    private final AnswerRepository answerRepository;
    private final ImageRepository  imageRepository;

    @Override
    public ServiceResult getAllPost(int page, int size) {
        Page<Post> post = postRepository.findAllByOrderByPostDateDesc(PageRequest.of(page, size));
        return ServiceResult.success(post);
    }

    @Override
    public ServiceResult doPosting(DoPostingModel doPostingModel, List<MultipartFile> images) throws IOException {
        Optional<Member> optionalMember = memberRepository.findByEmail(doPostingModel.getPoster());
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
            return ServiceResult.fail("삭제된 계정입니다.");
        }

        if (Objects.equals(null, doPostingModel.getTitle()) || doPostingModel.getTitle().isBlank()) {
            return ServiceResult.fail("제목이 비었습니다.");
        }
        if (Objects.equals(null, doPostingModel.getContents()) || doPostingModel.getContents().isBlank()) {
            return ServiceResult.fail("내용이 비었습니다.");
        }
        Post post = Post.builder()
                .title(doPostingModel.getTitle())
                .contents(doPostingModel.getContents())
                .poster(doPostingModel.getPoster())
                .hits(0)
                .likes(0)
                .postDate(LocalDateTime.now())
                .postStatus(ALL)
                .build();
        postRepository.save(post);

        if (!Objects.equals(images, null)) {
            String imagePath = System.getProperty("user.home") + "/images/";
            for (var image : images) {
                String imageName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                image.transferTo(new File(imagePath + imageName));
                imageRepository.save(Image.builder()
                        .imageName(imageName)
                        .imagePath(imagePath + imageName)
                        .post(post)
                        .build());
            }
        }
        return ServiceResult.success(post);
    }

    @Override
    public ServiceResult clickPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }
        post.setHits(post.getHits() + 1);
        postRepository.save(post);
        List<Image>  images      = imageRepository.findAllByPost(post);
        List<Reply>  replies     = replyRepository.findAllByPostAndPostReplyStatus(post, ALL);
        List<String> imageBase64 = new ArrayList<>();
        images.forEach(e -> {
            try {
                InputStream imageStream    = new FileInputStream(e.getImagePath());
                byte[]      imageByteArray = StreamUtils.copyToByteArray(imageStream); //image to byte-array
                imageStream.close();
                String image = Base64.getEncoder().encodeToString(imageByteArray);//byte-array to base64
                imageBase64.add(image);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        List<List<Answer>> allAnswers;

        if (!Objects.equals(replies, null)) {
            allAnswers = new ArrayList<>(replies.size());
            replies.forEach(reply -> {
                List<Answer> answers = answerRepository.findAllByReplyAndAnswerStatus(reply, ALL);
                if (!answers.isEmpty()) {
                    allAnswers.add(answers);
                }
            });
        } else {
            allAnswers = null;
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("post", post);
        map.put("images", imageBase64);
        map.put("reply", replies);
        map.put("answer", allAnswers);
        return ServiceResult.success(map);
    }

    @Override
    public ServiceResult delete(Long postId, MemberLogin memberLogin) {
        Optional<Post> optionalPost = postRepository.findById(postId);
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
        post.setDeleteDate(LocalDateTime.now());
        postRepository.save(post);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult clickLikes(Long postId, String token) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        long   count  = likesRepository.countLikesByPostAndMember(post, member);
        if (count > 0) {
            return ServiceResult.fail("이미 추천한 게시글입니다.");
        }

        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);

        Likes likes = Likes.builder()
                .member(member)
                .post(post)
                .build();
        likesRepository.save(likes);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult writeReply(Long postId, String token, PostReplyDto postReplyDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ServiceResult.fail("존재하지않는 게시글입니다.");
        }
        Post post = optionalPost.get();
        if (post.getPostStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 게시글입니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Reply reply = Reply.builder()
                .writer(postReplyDto.getWriter())
                .replyContents(postReplyDto.getReplyContents())
                .postReplyStatus(ALL)
                .replyDate(LocalDateTime.now())
                .post(post)
                .build();

        replyRepository.save(reply);
        return ServiceResult.success(reply);
    }

    @Override
    public ServiceResult deleteReply(Long replyId, String token, DeleteReplyDto deleteReplyDto) {
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        if (optionalReply.isEmpty()) {
            return ServiceResult.fail("댓글이 존재하지않습니다.");
        }
        Reply reply = optionalReply.get();

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();

        if (!member.getEmail().equals(reply.getWriter())) {
            return ServiceResult.fail("사용자 정보가 다릅니다.");
        }

        if (PasswordUtils.isNotEqual(deleteReplyDto.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 다릅니다.");
        }
        reply.setPostReplyStatus(DELETED);
        replyRepository.save(reply);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult writeAnswer(Long replyId, String token, WriteAnswerDto writeAnswerDto) {
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        if (optionalReply.isEmpty()) {
            return ServiceResult.fail("댓글이 존재하지않습니다.");
        }
        Reply reply = optionalReply.get();
        if (reply.getPostReplyStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 댓글입니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();

//        if (!member.getEmail().equals(writeAnswerDto.getWriter())) {
//            return ServiceResult.fail("사용자 정보가 다릅니다.");
//        }
        Answer answer = Answer.builder()
                .writer(writeAnswerDto.getWriter())
                .answerContents(writeAnswerDto.getAnswerContents())
                .answerStatus(ALL)
                .answerDate(LocalDateTime.now())
                .reply(reply)
                .build();
        answerRepository.save(answer);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteAnswer(Long answerId, String token, DeleteAnswerDto deleteAnswerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isEmpty()) {
            return ServiceResult.fail("답글이 존재하지않습니다.");
        }
        Answer answer = optionalAnswer.get();
        if (answer.getAnswerStatus().equals(DELETED)) {
            return ServiceResult.fail("삭제된 답글입니다.");
        }

        try {
            JwtUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            return ServiceResult.fail("토큰 인증이 잘못되었습니다.");
        }

        String           issuer         = JwtUtils.getIssuer(token);
        Optional<Member> optionalMember = memberRepository.findByEmail(issuer);
        if (optionalMember.isEmpty()) {
            return ServiceResult.fail("존재하지않는 계정입니다.");
        }
        Member member = optionalMember.get();
        if (PasswordUtils.isNotEqual(deleteAnswerDto.getPassword(), member.getPassword())) {
            return ServiceResult.fail("비밀번호가 일치하지않습니다.");
        }
        answer.setAnswerStatus(DELETED);
        answerRepository.save(answer);
        return ServiceResult.success();
    }
}
