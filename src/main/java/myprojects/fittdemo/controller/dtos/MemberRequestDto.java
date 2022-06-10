package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class MemberRequestDto {

    private Long memberId;
    private int yearOfBirth;
    private int monthOfBirth;
    private int dateOfBirth;
}
