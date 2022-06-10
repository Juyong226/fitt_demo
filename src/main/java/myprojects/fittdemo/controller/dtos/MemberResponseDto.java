package myprojects.fittdemo.controller.dtos;

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
    private String nickname;
    private LocalDate dateOfBirth;
    private LocalDate dateOfJoin;
    private List<BigFourResonseDto> bigFourResonseDtos;

    public void initialize(Member member) {
        setMemberId(member.getId());
        setName(member.getName());
        setNickname(member.getNickname());
        setDateOfJoin(member.getDateOfJoin());
        setDateOfBirth(member.getDateOfBirth());
        setBigFourResonseDtos(member.getBigFours());
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
