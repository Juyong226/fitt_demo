package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.TrainingSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TrainingSessionRepository {

    private final EntityManager em;

    public void save(TrainingSession trainingSession) {
            em.persist(trainingSession);
    }

    public TrainingSession findOne(Long trainingSessionId) {
        return em.find(TrainingSession.class, trainingSessionId);
    }

    public TrainingSession findWithFetch(Long trainingSessionId) {
        if (findOne(trainingSessionId) != null) {
            return em.createQuery(
                    "select distinct t from TrainingSession t" +
                            " join fetch t.sessionWorkouts sw" +
                            " join fetch sw.workout w" +
                            " where t.id = :trainingSessionId", TrainingSession.class)
                    .setParameter("trainingSessionId", trainingSessionId)
                    .getSingleResult();
        }
        return null;
    }

    public void remove(TrainingSession trainingSession) {
        em.remove(trainingSession);
    }
}
