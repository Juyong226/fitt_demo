package myprojects.fittdemo.controller;

import lombok.Data;

@Data
public class RoundRequestDto {

    private Long sessionWorkoutId;
    private Long roundId;
    private double weight;
    private int reps;

    public void initialize(RoundResponseDto responseDto) {
        setSessionWorkoutId(responseDto.getSessionWorkoutId());
        setRoundId(responseDto.getRoundId());
        setWeight(responseDto.getWeight());
        setReps(responseDto.getReps());
    }
}
