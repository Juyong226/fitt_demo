package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.SessionWorkout;
import myprojects.fittdemo.domain.TrainingSession;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingSessionResponseDto {

    private boolean isExist = true;
    private Long recordId;
    private Long trainingSessionId;
    private String title;
    private double totalVolume;
    private int sessionWorkoutCount;
    private List<SessionWorkoutResponseDto> sessionWorkoutResponseDtos = new ArrayList<>();

    public TrainingSessionResponseDto() {}
    public TrainingSessionResponseDto(Long recordId, Long trainingSessionId, String title) {
        setRecordId(recordId);
        setTrainingSessionId(trainingSessionId);
        setTitle(title);
    }

    public void initialize(TrainingSession trainingSession) {
        setRecordId(trainingSession.getRecord().getId());
        setTrainingSessionId(trainingSession.getId());
        setTitle(trainingSession.getTitle());
        setTotalVolume(trainingSession.calAndSetTotalVolume());
        setSessionWorkoutResponseDtos(trainingSession.getSessionWorkouts());
        setSessionWorkoutCount(sessionWorkoutResponseDtos.size());

    }

    private void setSessionWorkoutResponseDtos(List<SessionWorkout> sessionWorkouts) {
        List<SessionWorkoutResponseDto> responseDtos = new ArrayList<>();
        if (!sessionWorkouts.isEmpty()) {
            for (SessionWorkout sessionWorkout : sessionWorkouts) {
                SessionWorkoutResponseDto swrd = new SessionWorkoutResponseDto();
                swrd.initialize(sessionWorkout);
                responseDtos.add(swrd);
            }
        }
        this.sessionWorkoutResponseDtos = responseDtos;
    }
}
