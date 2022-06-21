package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class BigFourRequestDto {

    private Long memberId;
    private Long bigFourId;

    private double squat;
    private double benchPress;
    private double deadlift;
    private double overheadPress;

    public int validate() throws IllegalStateException {
        if (memberId == null || memberId <= 0) {
            throw new IllegalStateException("유효한 member Id 값이 아닙니다.");
        }
        if (squat < 0 || benchPress < 0 || deadlift < 0 || overheadPress < 0) {
            throw new IllegalStateException("유효한 기록 값이 아닙니다.");
        }
        if (bigFourId == null || bigFourId <= 0) {
            return 0;
        }
        return 1;
    }
}
