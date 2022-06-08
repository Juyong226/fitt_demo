package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.MuscleCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MuscleCategoryRepository {

    private final EntityManager em;

    public void save(MuscleCategory muscleCategory) {
        em.persist(muscleCategory);
    }

    public List<MuscleCategory> findAll() {
        return em.createQuery("select mc from MuscleCategory mc", MuscleCategory.class)
                .getResultList();
    }

    public List<MuscleCategory> findByIds(List<Long> muscleCategoryIds) {
        return em.createQuery(
                "select mc from MuscleCategory mc" +
                        " where mc.id in :muscleCategoryIds", MuscleCategory.class)
                .setParameter("muscleCategoryIds", muscleCategoryIds)
                .getResultList();
    }

    public void remove(MuscleCategory muscleCategory) {
        em.remove(muscleCategory);
    }
}
