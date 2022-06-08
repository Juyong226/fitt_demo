package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.Round;
import myprojects.fittdemo.domain.SessionWorkout;

import java.util.ArrayList;
import java.util.List;

@Data
public class SessionWorkoutResponseDto {

    private boolean isExist = true;
    private Long trainingSessionId;
    private Long sessionWorkoutId;
    private Long workoutId;
    private String workoutName;
    private double volume;
    private List<RoundResponseDto> roundResponseDtos = new ArrayList<>();

    public void initialize(SessionWorkout sessionWorkout) {
        setTrainingSessionId(sessionWorkout.getTrainingSession().getId());
        setSessionWorkoutId(sessionWorkout.getId());
        setWorkoutId(sessionWorkout.getWorkout().getId());
        setWorkoutName(sessionWorkout.getWorkout().getName());
        setVolume(sessionWorkout.getVolume());
        setRoundResponseDtos(sessionWorkout.getRounds());
    }

    private void setRoundResponseDtos(List<Round> rounds) {
        List<RoundResponseDto> responseDtos = new ArrayList<>();
        if (!rounds.isEmpty()) {
            for (Round round : rounds) {
                RoundResponseDto rrd = new RoundResponseDto();
                rrd.initialize(round);
                responseDtos.add(rrd);
            }
        }
        this.roundResponseDtos = responseDtos;
    }
}
