package myprojects.fittdemo.controller.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingSessionRequestDto {

    private Long recordId;
    private Long trainingSessionId;
    private String title;
    private List<SessionWorkoutRequestDto> sessionWorkoutRequestDtos = new ArrayList<>();

}
