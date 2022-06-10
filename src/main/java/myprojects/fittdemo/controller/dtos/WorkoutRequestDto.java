package myprojects.fittdemo.controller.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutRequestDto {

    private Long workoutId;
    private String workoutName;
    private List<Long> muscleCategoryIds = new ArrayList<>();
}
