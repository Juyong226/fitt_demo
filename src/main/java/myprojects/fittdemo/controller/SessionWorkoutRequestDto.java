package myprojects.fittdemo.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SessionWorkoutRequestDto {

    private Long trainingSessionId;
    private Long sessionWorkoutId;
    private Long workoutId;
    private List<RoundRequestDto> roundRequestDtos = new ArrayList<>();

    public void initialize(SessionWorkoutResponseDto responseDto) {
        setTrainingSessionId(responseDto.getTrainingSessionId());
        setSessionWorkoutId(responseDto.getSessionWorkoutId());
        setWorkoutId(responseDto.getWorkoutId());
        setRoundRequestDtos1(responseDto.getRoundResponseDtos());
    }

    private void setRoundRequestDtos1(List<RoundResponseDto> roundResponseDtos) {
        List<RoundRequestDto> requestDtos = new ArrayList<>();
        for (RoundResponseDto responseDto : roundResponseDtos) {
            RoundRequestDto requestDto = new RoundRequestDto();
            requestDto.initialize(responseDto);
            requestDtos.add(requestDto);
        }
        this.roundRequestDtos = requestDtos;
    }
}
