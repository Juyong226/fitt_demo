package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class RecordRequestDto {

    private Long recordId;
    private double bodyWeight;
    private String memo;
}
