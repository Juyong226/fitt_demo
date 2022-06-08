package myprojects.fittdemo.domain;

import lombok.Getter;
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
        name = "training_session_seq_generator",
        sequenceName = "training_session_seq",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "training_session_seq")
    @Column(name = "training_session_id")
    private Long id;

    private String title;
    private double totalVolume;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @OneToMany(mappedBy = "trainingSession", cascade = PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<SessionWorkout> sessionWorkouts = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected TrainingSession() {}

    public static TrainingSession create(String title) {
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.initialize(title);
        return trainingSession;
    }

    private void initialize(String title) {
        this.title = title;
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(Record record) {
        detachFromParent();
        this.record = record;
        record.getTrainingSessions().add(this);
    }

    public void detachFromParent() {
        if (this.record != null) {
            this.record.getTrainingSessions().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public void update(String title) {
        this.title = title;
    }

    public double calAndSetTotalVolume() {
        double sum = 0;
        for (SessionWorkout sw : sessionWorkouts) {
            sum += sw.calAndSetVolume();
        }
        this.totalVolume = sum;
        return sum;
    }
}
