package myprojects.fittdemo.domain;

import lombok.Getter;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "workout_muscle_category_seq_generator",
        sequenceName = "workout_muscle_category_seq",
        initialValue = 1,
        allocationSize = 1
)

@Getter
public class WorkoutMuscleCategory {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "workout_muscle_category_seq")
    @Column(name = "workout_muscle_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "muscle_category_id")
    private MuscleCategory muscleCategory;

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected WorkoutMuscleCategory() {}

    public static WorkoutMuscleCategory create(Workout workout, MuscleCategory muscleCategory) {
        WorkoutMuscleCategory workoutMuscleCategory = new WorkoutMuscleCategory();
        workoutMuscleCategory.Initialize(workout, muscleCategory);
        return workoutMuscleCategory;
    }

    private void Initialize(Workout workout, MuscleCategory muscleCategory) {
        relateTo(workout);
        relateTo(muscleCategory);
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(MuscleCategory muscleCategory) {
        this.muscleCategory = muscleCategory;
    }

    public void relateTo(Workout workout) {
        detachFromParent();
        this.workout = workout;
        workout.getWorkoutMuscleCategories().add(this);
    }

    public void detachFromParent() {
        if (this.workout != null) {
            this.workout.getWorkoutMuscleCategories().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public boolean hasPairIn(List<Long> muscleCategoryIds) {
        for (Long muscleCategoryId : muscleCategoryIds) {
            if (muscleCategory.getId() == muscleCategoryId) return true;
        }
        return false;
    }
}
