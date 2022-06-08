package myprojects.fittdemo.controller;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingSessionRequestDto {

    private Long recordId;
    private Long trainingSessionId;
    private String title;
    private List<SessionWorkoutRequestDto> sessionWorkoutRequestDtos = new ArrayList<>();

    public void initialize(TrainingSessionResponseDto responseDto) {
        setRecordId(responseDto.getRecordId());
        setTrainingSessionId(responseDto.getTrainingSessionId());
        setTitle(responseDto.getTitle());
        setSessionWorkoutRequestDtos1(responseDto.getSessionWorkoutResponseDtos());
    }

    private void setSessionWorkoutRequestDtos1(List<SessionWorkoutResponseDto> sessionWorkoutResponseDtos) {
        List<SessionWorkoutRequestDto> requestDtos = new ArrayList<>();
        for (SessionWorkoutResponseDto responseDto : sessionWorkoutResponseDtos) {
            SessionWorkoutRequestDto requestDto = new SessionWorkoutRequestDto();
            requestDto.initialize(responseDto);
            requestDtos.add(requestDto);
        }
        this.sessionWorkoutRequestDtos = requestDtos;
    }
}
