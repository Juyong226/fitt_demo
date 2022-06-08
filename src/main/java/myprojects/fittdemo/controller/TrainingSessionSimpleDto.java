package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.TrainingSession;

@Data
public class TrainingSessionSimpleDto {

    private Long trainingSessionId;
    private String title;
    private double totalVolume;

    public void initialize(TrainingSession trainingSession) {
        setTrainingSessionId(trainingSession.getId());
        setTitle(trainingSession.getTitle());
        setTotalVolume(trainingSession.getTotalVolume());
    }
}
