package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.Round;

@Data
public class RoundResponseDto {

    private boolean isExist = true;
    private Long sessionWorkoutId;
    private Long roundId;
    private double weight;
    private int reps;

    public void initialize(Round round) {
        setSessionWorkoutId(round.getSessionWorkout().getId());
        setRoundId(round.getId());
        setWeight(round.getWeight());
        setReps(round.getReps());
    }
}
