package myprojects.fittdemo.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;
import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        initialValue = 1,
        allocationSize = 1
)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
                    generator = "member_seq_generator")
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String nickname;
    private String password;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoin;

    @OneToMany(mappedBy = "member")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<BigFour> bigFours = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Record> records = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 정적 생성 메서드
     * */
    public static Member create(String name, String nickname, String password,
                                LocalDate dateOfBirth, LocalDate dateOfJoin) {
        Member member = new Member();
        member.Initialize(name, nickname, password, dateOfBirth, dateOfJoin);
        return member;
    }

    private void Initialize(String name, String nickname, String password,
                            LocalDate dateOfBirth, LocalDate dateOfJoin) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoin = dateOfJoin;
    }

    /**
     * 비즈니스 로직
     * */
    public int getAge() {
        return (int) YEARS.between(dateOfBirth, LocalDate.now());
    }

    public void update(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
