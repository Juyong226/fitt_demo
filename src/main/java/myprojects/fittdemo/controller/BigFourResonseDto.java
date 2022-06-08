package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.BigFour;

import java.time.LocalDate;

@Data
public class BigFourResonseDto {

    private boolean isExist = true;
    private Long memberId;
    private Long bigFourId;
    private LocalDate dateOfRecord;

    private double squat;
    private double benchPress;
    private double deadlift;
    private double overheadPress;
    private double bigThreeTotal;
    private double bigFourTotal;

    public void initialize(BigFour bigFour) {
        setMemberId(bigFour.getMember().getId());
        setBigFourId(bigFour.getId());
        setDateOfRecord(bigFour.getDateOfRecord());
        setSquat(bigFour.getSquat());
        setBenchPress(bigFour.getBenchPress());
        setDeadlift(bigFour.getDeadlift());
        setOverheadPress(bigFour.getOverheadPress());
        setBigFourTotal(bigFour.Total());
        setBigThreeTotal(bigFour.bigThreeTotal());

    }
}
