package myprojects.fittdemo.controller;

import lombok.Data;

@Data
public class BigFourRequestDto {

    private Long memberId;
    private Long bigFourId;

    private double squat;
    private double benchPress;
    private double deadlift;
    private double overheadPress;
}
