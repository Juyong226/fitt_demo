package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.*;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.domain.Record;
import myprojects.fittdemo.domain.Workout;
import myprojects.fittdemo.repository.MemberRepository;
import myprojects.fittdemo.repository.RecordRepository;
import myprojects.fittdemo.repository.TrainingSessionRepository;
import myprojects.fittdemo.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TrainingSessionServiceTest {

    @Autowired
    TrainingSessionService trainingSessionService;
    @Autowired
    TrainingSessionRepository trainingSessionRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    EntityManager em;

    @Test
    public void create_training_session_test() {
        // given
        Long recordId = 2L;
        String title = "두번째 운동";

        // when
        TrainingSessionResponseDto responseDto = trainingSessionService.create(recordId, title);
        TrainingSessionResponseDto result = trainingSessionService.find(0, responseDto.getTrainingSessionId());

        // then
        assertEquals(recordId, result.getRecordId());
        assertEquals(responseDto.getTrainingSessionId(), result.getTrainingSessionId());
        assertEquals(title, result.getTitle());
    }

    @Test
    @Rollback(value = false)
    public void create_training_session_test_dto() {
        // given
        TrainingSessionRequestDto tsrd = newRequestDto(4L, "오전 운동");

        // when
        TrainingSessionResponseDto result = trainingSessionService.create(tsrd);

        // then
        assertEquals(tsrd.getRecordId(), result.getRecordId());
        assertEquals(tsrd.getTitle(), result.getTitle());
        assertEquals(tsrd.getSessionWorkoutRequestDtos().size(), result.getSessionWorkoutResponseDtos().size());
    }

    @Test
    @Rollback(value = false)
    public void find_training_session_test() {
        // given
        Long recordId = 2L;
        String title = "운동 시작 첫 운동";
        TrainingSessionResponseDto created0 = trainingSessionService.create(recordId, title);
        TrainingSessionRequestDto tsrd = newRequestDto(3L, "오후 운동");
        TrainingSessionResponseDto created1 = trainingSessionService.create(tsrd);
        // when
        TrainingSessionResponseDto result0 =
                trainingSessionService.find(created0.getSessionWorkoutCount(), created0.getTrainingSessionId());
        TrainingSessionResponseDto result1 =
                trainingSessionService.find(created1.getSessionWorkoutCount(), created1.getTrainingSessionId());

        // then
        assertEquals(created0.getRecordId(), result0.getRecordId());
        assertEquals(created0.getTitle(), result0.getTitle());

        assertEquals(created1.getRecordId(), result1.getRecordId());
        assertEquals(created1.getTitle(), result1.getTitle());
        assertEquals(created1.getSessionWorkoutResponseDtos().size(), result1.getSessionWorkoutResponseDtos().size());
        assertEquals(created1.getTotalVolume(), result1.getTotalVolume());
    }

    @Test
    public void update_training_session_test() {
        // given
        TrainingSessionResponseDto responseDto = newTrainingSession();
        em.flush();
        em.clear();

        // when
        trainingSessionService.update(responseDto.getTrainingSessionId(), responseDto.getTitle());
        TrainingSessionResponseDto result =
                trainingSessionService.find(responseDto.getSessionWorkoutResponseDtos().size(),responseDto.getTrainingSessionId());

        // then
        assertEquals(responseDto.getTitle(), result.getTitle());
        assertEquals(responseDto.getSessionWorkoutResponseDtos().get(0).getRoundResponseDtos().get(0).getWeight(),
                result.getSessionWorkoutResponseDtos().get(0).getRoundResponseDtos().get(0).getWeight());
    }

    private TrainingSessionRequestDto responseToRequest(TrainingSessionResponseDto responseDto) {
        TrainingSessionRequestDto requestDto = new TrainingSessionRequestDto();
        requestDto.initialize(responseDto);
        return requestDto;
    }

    @Test
    public void remove_training_session_test() {
        // given
        TrainingSessionResponseDto responseDto = newTrainingSession();

        // when
        trainingSessionService.remove(responseDto.getTrainingSessionId());

        // then
        assertThrows(IllegalStateException.class,
                () -> trainingSessionService.find(
                        responseDto.getSessionWorkoutResponseDtos().size(), responseDto.getTrainingSessionId()));

    }

    private TrainingSessionResponseDto newTrainingSession() {
        TrainingSessionRequestDto requestDto = newRequestDto(5L, "오전 운동");
        TrainingSessionResponseDto responseDto = trainingSessionService.create(requestDto);
        return responseDto;
    }

    private TrainingSessionRequestDto newRequestDto(long memberId, String title) {
        Member member = memberRepository.findOne(memberId);
        Record record = recordRepository.findByMemberAndDate(member, LocalDate.now()).get(0);
        TrainingSessionRequestDto requestDto = new TrainingSessionRequestDto();
        requestDto.setRecordId(record.getId());
        requestDto.setTitle(title);
        requestDto.setSessionWorkoutRequestDtos(createSessionWorkoutDtos());
        return requestDto;
    }

    private List<SessionWorkoutRequestDto> createSessionWorkoutDtos() {
        List<SessionWorkoutRequestDto> swrds = new ArrayList<>();
        for (int i=1; i<4; i++) {
            SessionWorkoutRequestDto swrd = new SessionWorkoutRequestDto();
            Workout workout = workoutRepository.findOne((long) i);
            List<RoundRequestDto> rrds = new ArrayList<>();
            for (int j=0; j<5; j++) {
                RoundRequestDto rrd = new RoundRequestDto();
                rrd.setSessionWorkoutId(0L);
                rrd.setRoundId(0L);
                rrd.setReps(12 - j);
                rrd.setWeight(40.0);
                rrds.add(rrd);
            }
            swrd.setTrainingSessionId(0L);
            swrd.setSessionWorkoutId(0L);
            swrd.setWorkoutId(workout.getId());
            swrd.setRoundRequestDtos(rrds);
            swrds.add(swrd);
        }
        return swrds;
    }

    private List<SessionWorkoutRequestDto> moreSessionWorkoutDtos() {
        List<SessionWorkoutRequestDto> swrds = new ArrayList<>();
        for (int i=1; i<3; i++) {
            SessionWorkoutRequestDto swrd = new SessionWorkoutRequestDto();
            Workout workout = workoutRepository.findOne((long) i);
            List<RoundRequestDto> rrds = new ArrayList<>();
            for (int j=0; j<3; j++) {
                RoundRequestDto rrd = new RoundRequestDto();
                rrd.setSessionWorkoutId(0L);
                rrd.setRoundId(0L);
                rrd.setReps(5 - j);
                rrd.setWeight(60.0);
                rrds.add(rrd);
            }
            swrd.setTrainingSessionId(0L);
            swrd.setSessionWorkoutId(0L);
            swrd.setWorkoutId(workout.getId());
            swrd.setRoundRequestDtos(rrds);
            swrds.add(swrd);
        }
        return swrds;
    }
}