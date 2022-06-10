package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.dtos.BigFourRequestDto;
import myprojects.fittdemo.controller.dtos.BigFourResonseDto;
import myprojects.fittdemo.domain.BigFour;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.repository.BigFourRepository;
import myprojects.fittdemo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BigFourService {

    private final BigFourRepository bigFourRepository;
    private final MemberRepository memberRepository;

    /**
     * BigFour 추가 메서드
     *  - BigFour 데이터를 전달받아 객체를 생성한 후 해당 Member 객체와 연관 관계를 맺어 줌
     * @param: BigFourRequestDto
     * @return: Long bigFourId
     * */
    public BigFourResonseDto create(BigFourRequestDto requestDto) {
        Member member = memberRepository.findOne(requestDto.getMemberId());
        BigFour bigFour = BigFour.create(requestDto.getSquat(), requestDto.getBenchPress(),
                requestDto.getDeadlift(), requestDto.getOverheadPress());
        bigFour.relateTo(member);
        bigFourRepository.save(bigFour);
        return entityToResponseDto(bigFour);
    }

    public BigFourResonseDto update(BigFourRequestDto requestDto) {
        BigFour findOne = bigFourRepository.findOne(requestDto.getBigFourId());
        findOne.update(requestDto.getSquat(), requestDto.getBenchPress(),
                requestDto.getDeadlift(), requestDto.getOverheadPress());
        return entityToResponseDto(findOne);
    }

    public void remove(Long bigFourId) {
        BigFour findOne = bigFourRepository.findOne(bigFourId);
        bigFourRepository.remove(findOne);
    }

    //------------------------------------------------------------------------------------------------------------------
    private BigFourResonseDto entityToResponseDto(BigFour bigFour) {
        if (bigFour == null) return null;
        BigFourResonseDto responseDto = new BigFourResonseDto();
        responseDto.initialize(bigFour);
        return responseDto;
    }
}
