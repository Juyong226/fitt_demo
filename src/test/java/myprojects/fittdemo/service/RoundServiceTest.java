package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.dtos.*;
import myprojects.fittdemo.domain.Round;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoundServiceTest {

    @Autowired
    RoundService roundService;
    @Autowired
    TrainingSessionService trainingSessionService;
    @Autowired
    SessionWorkoutService sessionWorkoutService;
    @Autowired
    EntityManager em;

    @Test
    public void create_test() {
        // given
        TrainingSessionSimpleDto tResponseDto = newTrainingSession();
        SessionWorkoutResponseDto sResponseDto = newSessionWorkout(tResponseDto);
        RoundRequestDto requestDto = new RoundRequestDto();
        requestDto.setSessionWorkoutId(sResponseDto.getSessionWorkoutId());
        requestDto.setWeight(500);
        requestDto.setReps(1);

        // when
        RoundResponseDto result = roundService.create(requestDto);
        em.flush();

        //then
        assertEquals(sResponseDto.getSessionWorkoutId(), result.getSessionWorkoutId());
        assertEquals(requestDto.getWeight(), result.getWeight());
        assertEquals(requestDto.getReps(), result.getReps());
    }

    @Test
    public void update_test() {
        // given
        TrainingSessionSimpleDto tResponseDto = newTrainingSession();
        SessionWorkoutResponseDto sResponseDto = newSessionWorkout(tResponseDto);
        RoundRequestDto requestDto = new RoundRequestDto();
        requestDto.setSessionWorkoutId(sResponseDto.getSessionWorkoutId());
        requestDto.setWeight(500);
        requestDto.setReps(1);
        RoundResponseDto responseDto = roundService.create(requestDto);

        requestDto.setRoundId(responseDto.getRoundId());
        requestDto.setWeight(700);
        requestDto.setReps(15);

        // when
        RoundResponseDto result = roundService.update(requestDto);
        em.flush();

        // then
        assertEquals(requestDto.getWeight(), result.getWeight());
        assertEquals(requestDto.getReps(), result.getReps());
    }

    @Test
    @Rollback(value = false)
    public void remove_test() {
        // given
        TrainingSessionSimpleDto tResponseDto = newTrainingSession();
        SessionWorkoutResponseDto sResponseDto = newSessionWorkout(tResponseDto);
        RoundRequestDto requestDto = new RoundRequestDto();
        requestDto.setSessionWorkoutId(sResponseDto.getSessionWorkoutId());
        requestDto.setWeight(500);
        requestDto.setReps(1);
        RoundResponseDto responseDto = roundService.create(requestDto);

        // when
        roundService.remove(responseDto.getRoundId());

        // then
        assertEquals(null, em.find(Round.class, responseDto.getRoundId()));
    }

    private SessionWorkoutResponseDto newSessionWorkout(TrainingSessionSimpleDto tResponseDto) {
        Long workoutId = 1L;
        SessionWorkoutRequestDto requestDto = new SessionWorkoutRequestDto();
        requestDto.setTrainingSessionId(tResponseDto.getTrainingSessionId());
        requestDto.setWorkoutId(workoutId);
        requestDto.setRoundRequestDtos(newRounds());
        return sessionWorkoutService.create(requestDto);
    }

    private TrainingSessionSimpleDto newTrainingSession() {
        Long recordId = 1L;
        String title = "밤 운동";
        return trainingSessionService.create(recordId, title);
    }

    private List<RoundRequestDto> newRounds() {
        List<RoundRequestDto> rrds = new ArrayList<>();
        for (int j=0; j<3; j++) {
            RoundRequestDto rrd = new RoundRequestDto();
            rrd.setSessionWorkoutId(0L);
            rrd.setRoundId(0L);
            rrd.setReps(12 - j);
            rrd.setWeight(40.0);
            rrds.add(rrd);
        }
        return rrds;
    }
}