package myprojects.fittdemo.domain;

import lombok.Getter;
import myprojects.fittdemo.controller.dtos.SessionWorkoutRequestDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "session_workout_seq_generator",
        sequenceName = "session_workout_seq",
        initialValue = 1,
        allocationSize = 10
)
@Getter
public class SessionWorkout {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "session_workout_seq")
    @Column(name = "session_workout_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "training_session_id")
    private TrainingSession trainingSession;

    private double volume;

    @OneToMany(mappedBy = "sessionWorkout", cascade = PERSIST, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Round> rounds = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected SessionWorkout() {}

    public static SessionWorkout create(Workout workout) {
        SessionWorkout sessionWorkout = new SessionWorkout();
        sessionWorkout.initialize(workout);
        return sessionWorkout;
    }

    private void initialize(Workout workout) {
        this.workout = workout;
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(TrainingSession trainingSession) {
        detachFromParent();
        this.trainingSession = trainingSession;
        trainingSession.getSessionWorkouts().add(this);
    }

    public void detachFromParent() {
        if (this.trainingSession != null) {
            this.trainingSession.getSessionWorkouts().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public void update(Workout workout, List<Round> rounds) {
        this.workout = workout;
        this.rounds.clear();
        this.rounds = rounds;
    }

    public double calAndSetVolume() {
        double sum = 0;
        for(Round round : rounds) {
            sum += round.getVolume();
        }
        this.volume = sum;
        return sum;
    }

}
