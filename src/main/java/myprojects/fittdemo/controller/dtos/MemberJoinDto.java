package myprojects.fittdemo.controller.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberJoinDto {

    private String name;
    private String nickname;
    private String password;
    private LocalDate dateOfBirth;
}
