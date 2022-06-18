package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class RoundRequestDto {

    private Long sessionWorkoutId;
    private Long roundId;
    private double weight;
    private int reps;
}
