package myprojects.fittdemo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "muscle_category_seq_generator",
        sequenceName = "muscle_category_seq",
        initialValue = 1,
        allocationSize = 1
)

@Getter
public class MuscleCategory {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "muscle_category_seq")
    @Column(name = "muscle_category_id")
    private Long id;

    @Enumerated(STRING)
    private TargetMuscle targetMuscle;

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected MuscleCategory() {}

    public static MuscleCategory create(TargetMuscle targetMuscle) {
        MuscleCategory muscleCategory = new MuscleCategory();
        muscleCategory.initialize(targetMuscle);
        return muscleCategory;
    }

    private void initialize(TargetMuscle targetMuscle) {
        this.targetMuscle = targetMuscle;
    }
}
