package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.dtos.*;
import myprojects.fittdemo.repository.BigFourRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    BigFourService bigFourService;
    @Autowired
    BigFourRepository bigFourRepository;

    @Test
    @Rollback(value = false)
    public void join_test() {
        // given
        MemberJoinDto memberJoinDto = new MemberJoinDto();
        memberJoinDto.setName("이주용");
        memberJoinDto.setYear(1993);
        memberJoinDto.setMonth(02);
        memberJoinDto.setDayOfMonth(26);

        // when
        Long memberId = memberService.join(memberJoinDto);
        MemberResponseDto memberResponseDto = memberService.find(memberId);

        // then
        assertEquals(memberId, memberResponseDto.getMemberId());
    }

    @Test
    public void add_big_four_test() {
        // given
        Long memberId = 2L;
        double squat = 215.5;
        double benchPress = 150.0;
        double deadlift = 232.5;
        double overheadPress = 100.0;
        BigFourRequestDto bigFourRequestDto = new BigFourRequestDto();
        bigFourRequestDto.setMemberId(memberId);
        bigFourRequestDto.setSquat(squat);
        bigFourRequestDto.setBenchPress(benchPress);
        bigFourRequestDto.setDeadlift(deadlift);
        bigFourRequestDto.setOverheadPress(overheadPress);

        // when
        BigFourResonseDto resonseDto = bigFourService.create(bigFourRequestDto);
        MemberResponseDto memberResponseDto = memberService.findWithBigFours(memberId);

        // then
        assertEquals(1, resonseDto.getBigFourId());
        assertEquals(memberId, memberResponseDto.getMemberId());
        assertEquals(1, memberResponseDto.getBigFourResonseDtos().size());
        assertEquals(squat, memberResponseDto.getBigFourResonseDtos().get(0).getSquat());
        assertEquals(benchPress, memberResponseDto.getBigFourResonseDtos().get(0).getBenchPress());
        assertEquals(deadlift, memberResponseDto.getBigFourResonseDtos().get(0).getDeadlift());
        assertEquals(overheadPress, memberResponseDto.getBigFourResonseDtos().get(0).getOverheadPress());
        assertEquals((squat + benchPress + deadlift), memberResponseDto.getBigFourResonseDtos().get(0).getBigThreeTotal());
        assertEquals((squat + benchPress + deadlift + overheadPress), memberResponseDto.getBigFourResonseDtos().get(0).getBigFourTotal());

    }

    @Test
    public void update_test() {
        // given
        Long memberId = 3L;
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setMemberId(memberId);
        memberRequestDto.setYear(2000);
        memberRequestDto.setMonth(01);
        memberRequestDto.setDayOfMonth(01);

        // when
        MemberResponseDto updated = memberService.update(memberRequestDto);
        MemberResponseDto findOne = memberService.find(memberId);

        // then
        assertEquals(updated, findOne);
        assertEquals(updated.getMemberId(), findOne.getMemberId());
        assertEquals(updated.getDateOfJoin(), findOne.getDateOfJoin());
        assertEquals(LocalDate.of(2000, 01, 01), findOne.getDateOfBirth());
    }

    @Test
    public void withdraw_test() {
        // given
        Long memberId = 4L;

        // when
        memberService.withdraw(memberId);
        MemberResponseDto result = memberService.find(memberId);

        // then
        assertEquals(null, result);
    }
}