package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.RoundRequestDto;
import myprojects.fittdemo.controller.RoundResponseDto;
import myprojects.fittdemo.domain.Round;
import myprojects.fittdemo.domain.SessionWorkout;
import myprojects.fittdemo.repository.RoundRepository;
import myprojects.fittdemo.repository.SessionWorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;
    private final SessionWorkoutRepository sessionWorkoutRepository;

    /**
     * Round 단건 생성 메서드
     *  - 새로운 Round 를 생성하고 SessionWorkout 과 연관 관계를 맺어줌
     * @Param: RoundRequestDto
     * @return: RoundResponseDto
     * */
    public RoundResponseDto create(RoundRequestDto requestDto) {
        SessionWorkout sessionWorkout = sessionWorkoutRepository.findOne(requestDto.getSessionWorkoutId());
        Round round = Round.create(requestDto.getWeight(), requestDto.getReps());
        round.relateTo(sessionWorkout);
        roundRepository.save(round);
        return entityToResponseDto(round);
    }

    /**
     * Round 수정 메서드
     *  -  Round 를 조회하고, 엔티티 내무 상태 필드를 수정함
     * @param: RoundRequestDto
     * @return: RoundResponseDto
     * */
    public RoundResponseDto update(RoundRequestDto requestDto) {
        Round findOne = roundRepository.findOne(requestDto.getRoundId());
        findOne.update(requestDto.getWeight(), requestDto.getReps());
        return entityToResponseDto(findOne);
    }

    /**
     * Round 삭제 메서드
     *  - Round 를 조회하고, 엔티티를 삭제함
     * @param: Long roundId
     * */
    public void remove(Long roundId) {
        Round findOne = roundRepository.findOne(roundId);
//        findOne.detachFromParent();
        roundRepository.remove(findOne);
    }

    //------------------------------------------------------------------------------------------------------------------
    private RoundResponseDto entityToResponseDto(Round round) {
        if (round == null) return null;
        RoundResponseDto responseDto = new RoundResponseDto();
        responseDto.initialize(round);
        return responseDto;
    }
}
