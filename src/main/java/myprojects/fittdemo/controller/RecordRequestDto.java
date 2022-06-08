package myprojects.fittdemo.controller;

import lombok.Data;

@Data
public class RecordRequestDto {

    private Long recordId;
    private double bodyWeight;
    private String memo;
}
