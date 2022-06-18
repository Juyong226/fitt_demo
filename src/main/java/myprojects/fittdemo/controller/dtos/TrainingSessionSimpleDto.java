package myprojects.fittdemo.controller.dtos;

import lombok.Data;
import myprojects.fittdemo.domain.TrainingSession;

@Data
public class TrainingSessionSimpleDto {

    private Long recordId;
    private Long trainingSessionId;
    private String title;
    private int sessionWorkoutCount;
    private double totalVolume;

    public TrainingSessionSimpleDto() {}

    public TrainingSessionSimpleDto(TrainingSession trainingSession) {
        initialize(trainingSession);
    }

    public void initialize(TrainingSession trainingSession) {
        setRecordId(trainingSession.getRecord().getId());
        setTrainingSessionId(trainingSession.getId());
        setSessionWorkoutCount(trainingSession.getSessionWorkouts().size());
        setTitle(trainingSession.getTitle());
        setTotalVolume(trainingSession.getTotalVolume());
    }
}
