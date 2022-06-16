package myprojects.fittdemo.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "record_seq_generator",
        sequenceName = "record_seq",
        initialValue = 1,
        allocationSize = 1
)

@Getter
public class Record {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "record_seq_generator")
    @Column(name = "record_id")
    private Long id;

    private LocalDate dateOfRecord;
    private Boolean isRemoved;
    private LocalDateTime dateOfRemoval;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private double bodyWeight;

    @OneToMany(mappedBy = "record", cascade = PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<TrainingSession> trainingSessions = new ArrayList<>();

    private String memo;

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    protected Record() {}

    public static Record create(Member member, LocalDate dateOfRecord) {
        Record record = new Record();
        record.initialize(member, dateOfRecord);
        return record;
    }

    private void initialize(Member member, LocalDate dateOfRecord) {
        relateTo(member);
        this.dateOfRecord = dateOfRecord;
        this.isRemoved = false;
    }

    /**
     * 연관 관계 메서드
     * */
    public void relateTo(Member member) {
        detachFromParent();
        this.member = member;
        member.getRecords().add(this);
    }

    public void detachFromParent() {
        if (this.member != null) {
            this.member.getRecords().remove(this);
        }
    }

    /**
     * 비즈니스 로직
     * */
    public void update(LocalDate dateOfRecord, double bodyWeight, String memo) {
        this.dateOfRecord = dateOfRecord;
        this.bodyWeight = bodyWeight;
        this.memo = memo;
    }

    public void remove() {
        this.isRemoved = true;
        this.dateOfRemoval = LocalDateTime.now();
    }

}
