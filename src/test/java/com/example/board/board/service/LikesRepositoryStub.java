package com.example.board.board.service;

import com.example.board.board.entity.Likes;
import com.example.board.board.entity.Post;
import com.example.board.board.repository.LikesRepository;
import com.example.board.member.entity.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class LikesRepositoryStub implements LikesRepository {
    private final List<Optional<Likes>> postLikesTable = new ArrayList<>(2);

    @Override
    public Optional<Likes> findById(Long aLong) {
        for (var optional : postLikesTable) {
            Likes likes = optional.get();
            if (likes.getLikesId().equals(aLong)) {
                return optional;
            }
        }
        return Optional.empty();
    }

    @Override
    public <S extends Likes> S save(S entity) {
        for (var optional : postLikesTable) {
            Likes likes = optional.get();
            if (likes.getLikesId().equals(entity.getLikesId())) {
                int idx = postLikesTable.indexOf(optional);
                postLikesTable.set(idx, Optional.of(entity));
                return null;
            }
        }
        postLikesTable.add(Optional.of(entity));
        return null;
    }

    @Override
    public long countLikesByPostAndMember(Post post, Member member) {
        long cnt = 0;
        for (var optional : postLikesTable) {
            Likes likes = optional.get();
            if (likes.getPost().equals(post) && likes.getMember().equals(member)) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Likes> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Likes> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Likes> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Likes getOne(Long aLong) {
        return null;
    }

    @Override
    public Likes getById(Long aLong) {
        return null;
    }

    @Override
    public Likes getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Likes> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Likes> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Likes> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Likes> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Likes> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Likes> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Likes, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Likes> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Likes> findAll() {
        return null;
    }

    @Override
    public List<Likes> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Likes entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Likes> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Likes> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Likes> findAll(Pageable pageable) {
        return null;
    }
}
