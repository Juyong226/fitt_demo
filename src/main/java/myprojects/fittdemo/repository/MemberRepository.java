package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long memberId) {
        Member member = em.find(Member.class, memberId);
        if (member == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        return member;
    }

    public List<Member> findWithBigFours(Long memberId) {
        List<Member> memberList = em.createQuery(
                                    "select m from Member m" +
                                            " join fetch m.bigFours bf" +
                                            " where m.id = : memberId", Member.class)
                                    .setParameter("memberId", memberId)
                                    .getResultList();
        if (memberList.size() == 0) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        return memberList;
    }

    public void remove(Member member) {
        em.remove(member);
    }

    public List<Member> findByNickname(String nickname) {
        return em.createQuery(
                "select m from Member m" +
                        " where m.nickname = :nickname", Member.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
