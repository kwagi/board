package com.example.board.member.service;

import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MemberRepositoryStub implements MemberRepository {
    private final List<Optional<Member>> memberTable = new ArrayList<>(2);

    public void memberTableSize() {
        System.out.println(memberTable.size());
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        for (Optional<Member> optionalMember : memberTable) {
            if (optionalMember.get().getEmail().equals(email)) {
                return optionalMember;
            }
        }
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Member> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Member> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Member> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Member getOne(Long aLong) {
        return null;
    }

    @Override
    public Member getById(Long aLong) {
        return null;
    }

    @Override
    public Member getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Member> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Member> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Member> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Member> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Member, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Member> S save(S entity) {
        for (Optional<Member> optionalMember : memberTable) {
            if (entity.getEmail().equals(optionalMember.get().getEmail())) {
                int idx = memberTable.indexOf(optionalMember);
                memberTable.set(idx, Optional.of(entity));
                return null;
            }
        }
        memberTable.add(Optional.of(entity));
        return null;
    }

    @Override
    public <S extends Member> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Member> findAll() {
        return null;
    }

    @Override
    public List<Member> findAllById(Iterable<Long> longs) {
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
    public void delete(Member entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Member> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return null;
    }
}