package com.example.board.post.service;

import com.example.board.post.entity.PostReply;
import com.example.board.post.repository.PostReplyRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PostReplyRepositoryStub implements PostReplyRepository {
    private final List<Optional<PostReply>> postReplyTable = new ArrayList<>(2);

    @Override
    public void flush() {

    }

    @Override
    public <S extends PostReply> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PostReply> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<PostReply> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PostReply getOne(Long aLong) {
        return null;
    }

    @Override
    public PostReply getById(Long aLong) {
        return null;
    }

    @Override
    public PostReply getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends PostReply> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PostReply> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PostReply> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PostReply> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PostReply> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PostReply> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PostReply, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends PostReply> S save(S entity) {
        for (var optional : postReplyTable) {
            if (entity.equals(optional.get())) {
                int idx = postReplyTable.indexOf(optional);
                postReplyTable.set(idx, Optional.of(entity));
                return null;
            }
        }
        postReplyTable.add(Optional.of(entity));
        return null;
    }

    @Override
    public <S extends PostReply> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<PostReply> findById(Long aLong) {
        for (var optional : postReplyTable) {
            PostReply postReply = optional.get();
            if (postReply.getPostReplyId().equals(aLong)) {
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
    public List<PostReply> findAll() {
        return null;
    }

    @Override
    public List<PostReply> findAllById(Iterable<Long> longs) {
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
    public void delete(PostReply entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends PostReply> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PostReply> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<PostReply> findAll(Pageable pageable) {
        return null;
    }
}
