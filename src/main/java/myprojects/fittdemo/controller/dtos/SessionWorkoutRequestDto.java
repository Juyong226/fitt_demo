package myprojects.fittdemo.controller.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SessionWorkoutRequestDto {

    private Long trainingSessionId;
    private Long sessionWorkoutId;
    private Long workoutId;
    private List<RoundRequestDto> roundRequestDtos = new ArrayList<>();

    public int nullCheck() throws IllegalStateException {
        if (trainingSessionId == null || trainingSessionId == 0) {
            throw new IllegalStateException("유효하지 않은 TrainingSession Id 입니다.");
        }
        if (workoutId == null || workoutId == 0) {
            throw new IllegalStateException("유효하지 않은 Workout Id 입니다.");
        }
        if (sessionWorkoutId == null || sessionWorkoutId == 0) {
            return 0;
        }
        return 1;
    }
}
