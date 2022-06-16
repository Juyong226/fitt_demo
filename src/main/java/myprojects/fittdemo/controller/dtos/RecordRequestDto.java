package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class RecordRequestDto {

    private Long recordId;

    private String dateOfRecord;

    private int year;
    private int month;
    private int date;

    private double bodyWeight;
    private String memo;
}
