package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.WorkoutMuscleCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkoutMuscleCategoryRepository {

    private final EntityManager em;

    public void save(WorkoutMuscleCategory workoutMuscleCategory) {
        em.persist(workoutMuscleCategory);
    }

    public WorkoutMuscleCategory findOne(Long workoutMuscleCategoryId) {
        return em.find(WorkoutMuscleCategory.class, workoutMuscleCategoryId);
    }

    public List<WorkoutMuscleCategory> findByIds(List<Long> workoutMuscleCategoryIds) {
        return em.createQuery(
                "select wmc from WorkoutMuscleCategory wmc" +
                        " where wmc.id in :workoutMuscleCategoryIds", WorkoutMuscleCategory.class)
                .setParameter("workoutMuscleCategoryIds", workoutMuscleCategoryIds)
                .getResultList();
    }
}
