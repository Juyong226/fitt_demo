package myprojects.fittdemo.controller.dtos;

import lombok.Data;

@Data
public class MemberJoinDto {

    private String name;
    private String nickname;
    private String password;
    private String dateOfBirth;
}
