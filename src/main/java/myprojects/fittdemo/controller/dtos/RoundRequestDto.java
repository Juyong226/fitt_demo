package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class RoundRequestDto {

    private Long sessionWorkoutId;
    private Long roundId;
    private double weight;
    private int reps;

    public int validation() throws IllegalStateException {
        if (sessionWorkoutId == null || sessionWorkoutId <= 0) {
            throw new IllegalStateException("유효하지 않은 SessionWorkout Id 입니다.");
        }
        if (weight < 0 || reps < 0) {
            throw new IllegalStateException("유효하지 않은 weight 또는 reps 값입니다.");
        }
        if (roundId == null || roundId <= 0) {
            return 0;
        }
        return 1;
    }
}
