package com.example.board.post.service;

import com.example.board.member.entity.Member;
import com.example.board.post.entity.PostLikes;
import com.example.board.post.repository.PostLikesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PostLikesRepositoryStub implements PostLikesRepository {
    private final List<Optional<PostLikes>> postLikesTable = new ArrayList<>(2);

    @Override
    public long countByPostLikesIdAndMember(Long id, Member member) {
        long cnt = 0;
        for (var optional : postLikesTable) {
            PostLikes postLikes = optional.get();
            if (postLikes.getPost().getPostId().equals(id) && postLikes.getMember().equals(member)) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PostLikes> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PostLikes> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PostLikes> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PostLikes getOne(Long aLong) {
        return null;
    }

    @Override
    public PostLikes getById(Long aLong) {
        return null;
    }

    @Override
    public PostLikes getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends PostLikes> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PostLikes> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PostLikes> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PostLikes> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PostLikes> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PostLikes> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PostLikes, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends PostLikes> S save(S entity) {
        for (var optional : postLikesTable) {
            PostLikes postLikes = optional.get();
            if (postLikes.getPostLikesId().equals(entity.getPostLikesId())) {
                int idx = postLikesTable.indexOf(optional);
                postLikesTable.set(idx, Optional.of(entity));
                return null;
            }
        }
        postLikesTable.add(Optional.of(entity));
        return null;
    }

    @Override
    public <S extends PostLikes> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<PostLikes> findById(Long aLong) {
        for (var optional : postLikesTable) {
            PostLikes postLikes = optional.get();
            if (postLikes.getPostLikesId().equals(aLong)) {
                return optional;
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<PostLikes> findAll() {
        return null;
    }

    @Override
    public List<PostLikes> findAllById(Iterable<Long> longs) {
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
    public void delete(PostLikes entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PostLikes> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PostLikes> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PostLikes> findAll(Pageable pageable) {
        return null;
    }
}
