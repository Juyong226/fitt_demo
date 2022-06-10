package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class MemberJoinDto {

    private String name;
    private int yearOfBirth;
    private int monthOfBirth;
    private int dateOfMonthOfBirth;
}
