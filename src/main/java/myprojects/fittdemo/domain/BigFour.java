package myprojects.fittdemo.domain;

import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "big_four_seq_generator",
        sequenceName = "big_four_seq",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class BigFour {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "big_four_seq_generator")
    @Column(name = "big_four_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate dateOfRecord;

    private double squat;
    private double benchPress;
    private double deadlift;
    private double overheadPress;

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    public static BigFour create(double squat, double benchPress, double deadlift, double overheadPress) {
        BigFour bigFour = new BigFour();
        bigFour.initialize(squat, benchPress, deadlift, overheadPress, LocalDate.now());
        return bigFour;
    }

    private void initialize(double squat, double benchPress, double deadlift,
                            double overheadPress, LocalDate dateOfRecord) {
        this.squat = squat;
        this.benchPress = benchPress;
        this.deadlift = deadlift;
        this.overheadPress = overheadPress;
        this.dateOfRecord = dateOfRecord;
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(Member member) {
        detatchFromParent(member);
        this.member = member;
        member.getBigFours().add(this);
    }

    public void detatchFromParent(Member member) {
        if (this.member != null) {
            member.getBigFours().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public void update(double squat, double benchPress, double deadlift, double overheadPress) {
        this.squat = squat;
        this.benchPress = benchPress;
        this.deadlift = deadlift;
        this.overheadPress = overheadPress;
    }
    public double Total() {
        return bigThreeTotal() + overheadPress;
    }

    public double bigThreeTotal() {
        return squat + benchPress + deadlift;
    }
}
