package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class MemberRequestDto {

    private Long memberId;
    private int year;
    private int month;
    private int dayOfMonth;
}
