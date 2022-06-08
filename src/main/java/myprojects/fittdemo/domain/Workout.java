package myprojects.fittdemo.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "workout_seq_generator",
        sequenceName = "workout_seq",
        initialValue = 1,
        allocationSize = 1
)

@Getter
public class Workout {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "workout_seq")
    @Column(name = "workout_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "workout", cascade = PERSIST, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<WorkoutMuscleCategory> workoutMuscleCategories = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected Workout() {}

    public static Workout create(String name) {
        Workout workout = new Workout();
        workout.initialize(name);
        return workout;
    }

    private void initialize(String name) {
        this.name = name;
    }

    /**
     * 비즈니스 로직
     * */
    public void addMuscleCategories(List<WorkoutMuscleCategory> workoutMuscleCategories) {
        this.workoutMuscleCategories = workoutMuscleCategories;
    }

    public void update(String name) {
        clearWorkoutMuscleCategories();
        this.name = name;
    }

    private void clearWorkoutMuscleCategories() {
        if (!this.workoutMuscleCategories.isEmpty()) {
            this.workoutMuscleCategories.clear();
        }
    }

    public boolean hasRelationsWith(List<Long> muscleCategoryIds) {
        if (workoutMuscleCategories.size() == muscleCategoryIds.size()) {
            for (WorkoutMuscleCategory wmc : workoutMuscleCategories) {
                if (wmc.hasPairIn(muscleCategoryIds) == false)
                    return false;
            }
            return true;
        } else return false;
    }
}
