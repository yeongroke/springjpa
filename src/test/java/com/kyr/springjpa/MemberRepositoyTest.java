package com.kyr.springjpa;

import com.kyr.springjpa.model.entity.Member;
import com.kyr.springjpa.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoyTest {

    @Autowired
    MemberRepository memberRepository;

    // test 어노테이션이 있으면 데이터가 들어가고 테스트가 끝나면 롤백시킴
    @Test
    @Transactional
    public void testmember() throws Exception {
        /*Member member = new Member();
        member.setUsername("test1");

        Long memberSaveId = memberRepository.save(member);
        Member findMember = memberRepository.findid(memberSaveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(memberSaveId);*/

    }
}
