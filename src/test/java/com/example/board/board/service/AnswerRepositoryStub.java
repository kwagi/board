package com.example.board.board.service;

import com.example.board.board.entity.Answer;
import com.example.board.board.repository.AnswerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AnswerRepositoryStub implements AnswerRepository {
    private final List<Optional<Answer>> answerTable = new ArrayList<>(2);

    @Override
    public Optional<Answer> findById(Long aLong) {
        for (var optional : answerTable) {
            Answer answer = optional.get();
            if (answer.getAnswerId().equals(aLong)) {
                return optional;
            }
        }
        return Optional.empty();
    }

    @Override
    public <S extends Answer> S save(S entity) {
        for (var optional : answerTable) {
            if (entity.equals(optional.get())) {
                int idx = answerTable.indexOf(optional);
                answerTable.set(idx, Optional.of(entity));
                return null;
            }
        }
        answerTable.add(Optional.of(entity));
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Answer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Answer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Answer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Answer getOne(Long aLong) {
        return null;
    }

    @Override
    public Answer getById(Long aLong) {
        return null;
    }

    @Override
    public Answer getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Answer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Answer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Answer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Answer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Answer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Answer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Answer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Answer> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Answer> findAll() {
        return null;
    }

    @Override
    public List<Answer> findAllById(Iterable<Long> longs) {
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
    public void delete(Answer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Answer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Answer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Answer> findAll(Pageable pageable) {
        return null;
    }
}
