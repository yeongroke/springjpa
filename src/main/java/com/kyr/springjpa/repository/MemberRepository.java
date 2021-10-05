package com.kyr.springjpa.repository;

import com.kyr.springjpa.model.bean.MemberDto;
import com.kyr.springjpa.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member save(Member member);

    @Query(value = "SELECT m.id , m.username , m.email , m.conpany FROM Member m WHERE m.id = :id")
    Optional<MemberDto> findByMemberid(Long id);
}
