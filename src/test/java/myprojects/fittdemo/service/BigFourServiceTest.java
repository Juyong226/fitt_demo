package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.dtos.BigFourRequestDto;
import myprojects.fittdemo.controller.dtos.BigFourResonseDto;
import myprojects.fittdemo.domain.BigFour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BigFourServiceTest {

    @Autowired
    BigFourService bigFourService;
    @Autowired
    EntityManager em;

    @Test
    public void create_test() {
        // given
        BigFourRequestDto requestDto = newRequestDto();
        double bigThree = requestDto.getSquat() + requestDto.getBenchPress() + requestDto.getDeadlift();
        double bigFour = bigThree + requestDto.getOverheadPress();
        // when
        BigFourResonseDto result = bigFourService.create(requestDto);

        // then
        assertEquals(requestDto.getMemberId(), result.getMemberId());
        assertEquals(bigThree, result.getBigThreeTotal());
        assertEquals(bigFour, result.getBigFourTotal());
    }

    @Test
    public void update_test() {
        // given
        BigFourRequestDto requestDto = newRequestDto();
        BigFourResonseDto responseDto = bigFourService.create(requestDto);
        // 수정용 requestDto 만들기
        double newSquat = 300.0;
        double newDeadlift = 310.0;
        double newOverheadPress = 120.5;
        requestDto.setBigFourId(responseDto.getBigFourId());
        requestDto.setSquat(newSquat);
        requestDto.setDeadlift(newDeadlift);
        requestDto.setOverheadPress(newOverheadPress);

        // when
        BigFourResonseDto result = bigFourService.update(requestDto);
        em.flush();

        // then
        assertEquals(responseDto.getBigFourId(), result.getBigFourId());
        assertEquals(newSquat, result.getSquat());
        assertEquals(newDeadlift, result.getDeadlift());
        assertEquals(newOverheadPress, result.getOverheadPress());
    }

    @Test
    public void remove_test() {
        // given
        BigFourRequestDto requestDto = newRequestDto();
        BigFourResonseDto resonseDto = bigFourService.create(requestDto);

        // when
        bigFourService.remove(resonseDto.getBigFourId());

        // then
        assertEquals(null, em.find(BigFour.class, resonseDto.getBigFourId()));
    }

    private BigFourRequestDto newRequestDto() {
        Long memberId = 1L;
        double squat = 255.5;
        double benchPress = 153.5;
        double deadlift = 270.0;
        double overheadPress = 112.5;
        BigFourRequestDto requestDto = new BigFourRequestDto();
        requestDto.setMemberId(memberId);
        requestDto.setSquat(squat);
        requestDto.setBenchPress(benchPress);
        requestDto.setDeadlift(deadlift);
        requestDto.setOverheadPress(overheadPress);
        return requestDto;
    }
}