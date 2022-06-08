package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.MuscleCategory;
import myprojects.fittdemo.domain.Workout;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkoutRepository {

    private final EntityManager em;

    public void save(Workout workout) {
        em.persist(workout);
    }

    public Workout findOne(Long workoutId) {
        return em.find(Workout.class, workoutId);
    }

    public List<Workout> findAll() {
        return em.createQuery("select w from Workout w", Workout.class)
                .getResultList();
    }

    public List<Workout> findByMuscleCategory(MuscleCategory muscleCategory) {
        return em.createQuery(
                "select w from Workout w" +
                        " join w.workoutMuscleCategories wmc" +
                        " where wmc.muscleCategory = :muscleCategory", Workout.class)
                .setParameter("muscleCategory", muscleCategory)
                .getResultList();
    }

    public List<Workout> findByMuscleCategories(List<Long> muscleCategoryIds) {
        return em.createQuery(
                "select distinct w from Workout w" +
                        " join fetch w.workoutMuscleCategories wmc" +
                        " join fetch wmc.muscleCategory mc" +
                        " where mc.id in :muscleCategoryIds", Workout.class)
                .setParameter("muscleCategoryIds", muscleCategoryIds)
                .getResultList();
    }

    public void remove(Workout workout) {
        em.remove(workout);
    }
}
