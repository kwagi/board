package com.example.board.board.service;

import com.example.board.board.entity.Reply;
import com.example.board.board.repository.ReplyRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ReplyRepositoryStub implements ReplyRepository {
    private final List<Optional<Reply>> postReplyTable = new ArrayList<>(2);

    @Override
    public void flush() {

    }

    @Override
    public <S extends Reply> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Reply> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Reply> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Reply getOne(Long aLong) {
        return null;
    }

    @Override
    public Reply getById(Long aLong) {
        return null;
    }

    @Override
    public Reply getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Reply> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Reply> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Reply> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Reply> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Reply> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Reply> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Reply, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Reply> S save(S entity) {
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
    public <S extends Reply> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Reply> findById(Long aLong) {
        for (var optional : postReplyTable) {
            Reply reply = optional.get();
            if (reply.getReplyId().equals(aLong)) {
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
    public List<Reply> findAll() {
        return null;
    }

    @Override
    public List<Reply> findAllById(Iterable<Long> longs) {
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
    public void delete(Reply entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Reply> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Reply> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Reply> findAll(Pageable pageable) {
        return null;
    }
}
