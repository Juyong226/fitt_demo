package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.dtos.*;
import myprojects.fittdemo.domain.SessionWorkout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SessionWorkoutServiceTest {

    @Autowired
    SessionWorkoutService sessionWorkoutService;
    @Autowired
    TrainingSessionService trainingSessionService;
    @Autowired
    EntityManager em;

    @Test
    public void create_test() {
        // given
        TrainingSessionSimpleDto tResponseDto =  newTrainingSession();
        Long workoutId = 1L;
        SessionWorkoutRequestDto requestDto = new SessionWorkoutRequestDto();
        requestDto.setTrainingSessionId(tResponseDto.getTrainingSessionId());
        requestDto.setWorkoutId(workoutId);
        requestDto.setRoundRequestDtos(newRounds());

        // when
        SessionWorkoutResponseDto result = sessionWorkoutService.create(requestDto);

        // then
        assertEquals(tResponseDto.getTrainingSessionId(), result.getTrainingSessionId());
        assertEquals(workoutId, result.getWorkoutId());
    }

    @Test
    public void remove_test() {
        // given
        TrainingSessionSimpleDto tResponseDto =  newTrainingSession();
        Long workoutId = 1L;
        SessionWorkoutRequestDto requestDto = new SessionWorkoutRequestDto();
        requestDto.setTrainingSessionId(tResponseDto.getTrainingSessionId());
        requestDto.setWorkoutId(workoutId);
        requestDto.setRoundRequestDtos(newRounds());
        SessionWorkoutResponseDto responseDto = sessionWorkoutService.create(requestDto);

        // when
        sessionWorkoutService.remove(responseDto.getSessionWorkoutId());
        SessionWorkout result = em.find(SessionWorkout.class, responseDto.getSessionWorkoutId());

        // then
        assertEquals(null, result);
    }

    private TrainingSessionSimpleDto newTrainingSession() {
        Long recordId = 1L;
        String title = "??? ??????";
        return trainingSessionService.create(recordId, title);
    }

    private List<RoundRequestDto> newRounds() {
        List<RoundRequestDto> rrds = new ArrayList<>();
        for (int j=0; j<5; j++) {
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