package myprojects.fittdemo.controller;

import lombok.Data;
import myprojects.fittdemo.domain.BigFour;
import myprojects.fittdemo.domain.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberResponseDto {

    private boolean isExist = true;
    private Long memberId;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoin;
    private List<BigFourResonseDto> bigFourResonseDtos;

    public void initialize(Member member) {
        this.setMemberId(member.getId());
        this.setName(member.getName());
        this.setDateOfJoin(member.getDateOfJoin());
        this.setDateOfBirth(member.getDateOfBirth());
        this.setBigFourResonseDtos(member.getBigFours());
    }

    private void setBigFourResonseDtos(List<BigFour> bigFours) {
        this.bigFourResonseDtos = bigFoursToResponseDtos(bigFours);
    }

    private List<BigFourResonseDto> bigFoursToResponseDtos(List<BigFour> bigFours) {
        List<BigFourResonseDto> bigFourResonseDtos = new ArrayList<>();
        if (!bigFours.isEmpty()) {
            for (BigFour bigFour : bigFours) {
                BigFourResonseDto bfrd = new BigFourResonseDto();
                bfrd.initialize(bigFour);
                bigFourResonseDtos.add(bfrd);
            }
        }
        return bigFourResonseDtos;
    }
}
