package myprojects.fittdemo.controller.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutRequestDto {

    private Long workoutId;
    private String workoutName;
    private List<Long> muscleCategoryIds = new ArrayList<>();

    public int validation() throws IllegalStateException {
        if (workoutName == null || workoutName == " ") {
            throw new IllegalStateException("workoutName을 입력해주세요.");
        }
        if (muscleCategoryIds.isEmpty()) {
            throw new IllegalStateException("하나 이상의 muscleCategory Id가 필요합니다.");
        }
        if (workoutId == null || workoutId <= 0) {
            return 0;
        }
        return 1;
    }
}
