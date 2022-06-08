package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.TargetMuscle;
import myprojects.fittdemo.domain.Workout;
import myprojects.fittdemo.domain.WorkoutMuscleCategory;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutResponseDto {

    private boolean isExist = true;
    private Long workoutId;
    private String workoutName;
    private List<TargetMuscle> targetMuscles = new ArrayList<>();

    public void initialize(Workout workout) {
        setWorkoutId(workout.getId());
        setWorkoutName(workout.getName());
        setTargetMuscles(workout.getWorkoutMuscleCategories());
    }

    public void setTargetMuscles(List<WorkoutMuscleCategory> workoutMuscleCategories) {
        for (WorkoutMuscleCategory workoutMuscleCategory : workoutMuscleCategories) {
            TargetMuscle targetMuscle = workoutMuscleCategory.getMuscleCategory().getTargetMuscle();
            this.targetMuscles.add(targetMuscle);
        }
    }
}
