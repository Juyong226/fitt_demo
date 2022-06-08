package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.*;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.repository.MemberRepository;
import myprojects.fittdemo.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class RecordServiceTest {

    @Autowired
    RecordService recordService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkoutRepository workoutRepository;

    @Test
    public void validate_duplicate_test() {
        // given
        Long memberId = 1L;

        // when

        // then
        assertThrows(IllegalStateException.class, () -> recordService.create(memberId));
    }

    @Test
    public void remove_record_test() {
        // given
        Long memberId = 1L;
        Member member = memberRepository.findOne(memberId);
        List<RecordResponseDto> recordFirst = recordService.find(member.getId(), LocalDate.now().toString());

        // when
        recordService.remove(recordFirst.get(0).getRecordId());
        List<RecordResponseDto> recordSecond = recordService.find(member.getId(), LocalDate.now().toString());

        // then
        assertEquals(0, recordSecond.size());
    }

    @Test
    public void update_state_field_test() {
        // given
        Long memberId = 2L;
        List<RecordResponseDto> recordOld = recordService.find(memberId, LocalDate.now().toString());

        RecordRequestDto recordRequestDto = new RecordRequestDto();
        recordRequestDto.setRecordId(recordOld.get(0).getRecordId());
        recordRequestDto.setBodyWeight(79.5);
        recordRequestDto.setMemo("테스트 중!");

        // when
        recordService.update(recordRequestDto);
        List<RecordResponseDto> recordNew = recordService.find(memberId, LocalDate.now().toString());

        // then
        assertEquals(recordRequestDto.getClass(), recordNew.get(0).getBodyWeight());
        assertEquals(recordRequestDto.getMemo(), recordNew.get(0).getMemo());
    }
}