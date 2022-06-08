package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.Round;
import myprojects.fittdemo.domain.SessionWorkout;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoundRepository {

    private final EntityManager em;

    public void save(Round round) {
        em.persist(round);
    }

    public Round findOne(Long roundId) {
        return em.find(Round.class, roundId);
    }

    public List<Round> findAll(SessionWorkout sessionWorkout) {
        return em.createQuery(
                "select r from Round r " +
                        "where r.sessionWorkout = :sessionWorkout", Round.class)
                .setParameter("sessionWorkout", sessionWorkout)
                .getResultList();
    }

    public void remove(Round round) {
        em.remove(round);
    }
}
