package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.SessionWorkout;
import myprojects.fittdemo.domain.TrainingSession;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SessionWorkoutRepository {

    private final EntityManager em;

    public void save(SessionWorkout sessionWorkout) {
        em.persist(sessionWorkout);
    }

    public SessionWorkout findOne(Long sessionWorkoutId) {
        return em.find(SessionWorkout.class, sessionWorkoutId);
    }

    public List<SessionWorkout> findAllBelongToASession(TrainingSession trainingSession) {
        return em.createQuery(
                "select sw from SessionWorkout sw" +
                        " where sw.trainingSession = :trainingSession")
                .setParameter("trainingSession", trainingSession)
                .getResultList();
    }

    public void remove(SessionWorkout sessionWorkout) {
        em.remove(sessionWorkout);
    }
}
