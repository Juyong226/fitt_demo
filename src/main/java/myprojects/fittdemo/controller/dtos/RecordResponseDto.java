package myprojects.fittdemo.controller.dtos;

import lombok.Data;
import myprojects.fittdemo.domain.Record;
import myprojects.fittdemo.domain.TrainingSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecordResponseDto {

    private boolean isExist = true;
    private Long recordId;
    private Long memberId;
    private String dateOfRecord;
    private double bodyWeight;
    private String memo;
    private List<TrainingSessionSimpleDto> trainingSessionSimpleDtos = new ArrayList<>();

    public void initialize(Record record) {
        setRecordId(record.getId());
        setMemberId(record.getMember().getId());
        setDateOfRecord(record.getDateOfRecord().toString());
        setBodyWeight(record.getBodyWeight());
        setMemo(record.getMemo());
        setTrainingSessionSimpleDtos(record.getTrainingSessions());
    }

    private void setTrainingSessionSimpleDtos(List<TrainingSession> trainingSessions) {
        List<TrainingSessionSimpleDto> simpleDtos = new ArrayList<>();
        if (!trainingSessions.isEmpty()) {
            for (TrainingSession trainingSession : trainingSessions) {
                TrainingSessionSimpleDto trainingSessionSimpleDto = new TrainingSessionSimpleDto();
                trainingSessionSimpleDto.initialize(trainingSession);
                simpleDtos.add(trainingSessionSimpleDto);
            }
        }
        this.trainingSessionSimpleDtos = simpleDtos;
    }
}
