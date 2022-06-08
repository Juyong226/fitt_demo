package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.BigFour;
import myprojects.fittdemo.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BigFourRepository {

    private final EntityManager em;

    public void save(BigFour bigFour) {
        em.persist(bigFour);
    }

    public BigFour findOne(Long bigFourId) {
        return em.find(BigFour.class, bigFourId);
    }

    public List<BigFour> findAll(Member member) {
        return em.createQuery(
                "select bf from BigFour bf" +
                        " where bf.member = :member", BigFour.class)
                .setParameter("member", member)
                .getResultList();
    }

    public void remove(BigFour bigFour) {
        em.remove(bigFour);
    }
}
