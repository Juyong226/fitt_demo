package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.dtos.RecordRequestDto;
import myprojects.fittdemo.controller.dtos.RecordResponseDto;
import myprojects.fittdemo.domain.Record;
import myprojects.fittdemo.domain.*;
import myprojects.fittdemo.repository.MemberRepository;
import myprojects.fittdemo.repository.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;

    /**
     * Record 단건 조회 메서드
     * @param: Long memberId, LocalDate dateOfRecord
     * @return: List<RecordRespondDto> / empty List<RecordResponseDto>
     * */
    @Transactional(readOnly = true)
    public RecordResponseDto find(Long memberId, LocalDate dateOfRecord) {
        Member member = memberRepository.findOne(memberId);
        List<Record> findRecords = recordRepository.findByMemberAndDate(member, dateOfRecord);
        return entitiesToResponseDto(findRecords);
    }

    @Transactional(readOnly = true)
    public RecordResponseDto find(Long memberId, String dateOfRecord) {
        Member member = memberRepository.findOne(memberId);
        List<Record> findRecords = recordRepository.findByMemberAndDate(member, LocalDate.parse(dateOfRecord));
        return entitiesToResponseDto(findRecords);
    }

    @Transactional
    public RecordResponseDto find(Long memberId, RecordRequestDto requestDto) {
        Member member = memberRepository.findOne(memberId);
        List<Record> findRecords =
                recordRepository.findByMemberAndDate(member,
                        LocalDate.of(requestDto.getYear(), requestDto.getMonth(), requestDto.getDate()));
        return entitiesToResponseDto(findRecords);
    }

    /**
     * Record 첫 생성 메서드
     * @param: Long memberId
     * @return: Long recordId
     * */
    public Long create(Long memberId) {
        Member member = memberRepository.findOne(memberId);
        validateDuplicateRecord(member);
        Record record = Record.create(member);
        recordRepository.save(record);
        return record.getId();
    }

    /**
     * Record 상태 필드 수정 메서드
     * @param: RecordDto
     * */
    public void update(RecordRequestDto requestDto) {
        Record record = recordRepository.findOne(requestDto.getRecordId());
        record.update(requestDto.getBodyWeight(), requestDto.getMemo());
    }

    /**
     * Record 삭제 메서드
     * @Param: Long recordId
     * */
    public void remove(Long recordId) {
        Record findOne = recordRepository.findOne(recordId);
        findOne.remove();
    }


    //------------------------------------------------------------------------------------------------------------------
    private RecordResponseDto entitiesToResponseDto(List<Record> findRecords) {
        if (!findRecords.isEmpty()) {
            RecordResponseDto responseDto = null;
            for (Record findRecord : findRecords) {
                responseDto = entityToResponseDto(findRecord);
            }
            return responseDto;
        } else return null;
    }

    private RecordResponseDto entityToResponseDto(Record record) {
        RecordResponseDto responseDto = new RecordResponseDto();
        responseDto.initialize(record);
        return responseDto;
    }

    private void validateDuplicateRecord(Member member) {
        List<Record> findRecords = recordRepository.findByMemberAndDate(member, LocalDate.now());
        if (!findRecords.isEmpty()) {
            throw new IllegalStateException("오늘 작성한 기록일지가 이미 존재합니다.");
        }
    }
}
