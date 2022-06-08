package myprojects.fittdemo.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "round_seq_generator",
        sequenceName = "round_seq",
        initialValue = 1,
        allocationSize = 50
)

@Getter
public class Round {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "round_seq")
    @Column(name = "round_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "session_workout_id")
    private SessionWorkout sessionWorkout;

    private double weight;
    private int reps;

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected Round() {}

    public static Round create(double weight, int reps) {
        Round round = new Round();
        round.initialize(weight, reps);
        return round;
    }

    private void initialize( double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(SessionWorkout sessionWorkout) {
        detachFromParent();
        this.sessionWorkout = sessionWorkout;
        sessionWorkout.getRounds().add(this);
    }

    public void detachFromParent() {
        if (this.sessionWorkout != null) {
            this.sessionWorkout.getRounds().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public double getVolume() {
        return weight * reps;
    }

    public void update(double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }
}
