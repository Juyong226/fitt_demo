package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.dtos.RoundRequestDto;
import myprojects.fittdemo.controller.dtos.SessionWorkoutRequestDto;
import myprojects.fittdemo.controller.dtos.SessionWorkoutResponseDto;
import myprojects.fittdemo.domain.Round;
import myprojects.fittdemo.domain.SessionWorkout;
import myprojects.fittdemo.domain.TrainingSession;
import myprojects.fittdemo.domain.Workout;
import myprojects.fittdemo.repository.SessionWorkoutRepository;
import myprojects.fittdemo.repository.TrainingSessionRepository;
import myprojects.fittdemo.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionWorkoutService {

    private final SessionWorkoutRepository sessionWorkoutRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final WorkoutRepository workoutRepository;

    /**
     * SessionWorkout 단건 추가 메서드
     *  - 새로운 SessionWorkout 을 생성하고 TrainingSession 과 연관관계를 맺어줌
     *  - SessionWorkout 내부의 Round 까지 한번에 저장함
     * @param: SessionWorkoutRequestDto
     * @return: SessionWorkoutResponseDto
     * */
    public SessionWorkoutResponseDto create(SessionWorkoutRequestDto requestDto) {
        TrainingSession trainingSession = trainingSessionRepository.findOne(requestDto.getTrainingSessionId());
        SessionWorkout sessionWorkout = convertToEntity(requestDto);
        sessionWorkout.relateTo(trainingSession);
        sessionWorkoutRepository.save(sessionWorkout);
        return entityToResponseDto(sessionWorkout);
    }

    public SessionWorkoutResponseDto update(SessionWorkoutRequestDto requestDto) {
        SessionWorkout sessionWorkout = sessionWorkoutRepository.findOne(requestDto.getSessionWorkoutId());
        Workout workout = workoutRepository.findOne(requestDto.getWorkoutId());
        List<Round> rounds = convertToRounds(requestDto.getRoundRequestDtos());
        sessionWorkout.update(workout, rounds);
        return entityToResponseDto(sessionWorkout);
    }

    /**
     * SessionWorkout 삭제 메서드
     *  - SessionWorkout 을 조회하고, 엔티티를 삭제함
     * @param: Long sessionWorkoutId
     * */
    public void remove(Long sessionWorkoutId) {
        SessionWorkout findOne = sessionWorkoutRepository.findOne(sessionWorkoutId);
        sessionWorkoutRepository.remove(findOne);
    }

    //------------------------------------------------------------------------------------------------------------------

    private SessionWorkoutResponseDto entityToResponseDto(SessionWorkout entity) {
        if (entity == null) return null;
        SessionWorkoutResponseDto responseDto = new SessionWorkoutResponseDto();
        responseDto.initialize(entity);
        return responseDto;
    }

    private SessionWorkout convertToEntity(SessionWorkoutRequestDto requestDto) {
        Workout workout = workoutRepository.findOne(requestDto.getWorkoutId());
        SessionWorkout entity = SessionWorkout.create(workout);
        connectEntities(entity, convertToRounds(requestDto.getRoundRequestDtos()));
        return entity;
    }

    private List<Round> convertToRounds(List<RoundRequestDto> requestDtos) {
        List<Round> entities = new ArrayList<>();
        if (!requestDtos.isEmpty()) {
            for (RoundRequestDto requestDto : requestDtos) {
                Round entity = Round.create(requestDto.getWeight(), requestDto.getReps());
                entities.add(entity);
            }
        }
        return entities;
    }

    private void connectEntities(SessionWorkout sessionWorkout, List<Round> rounds) {
        if (!rounds.isEmpty()) {
            for (Round round : rounds) {
                round.relateTo(sessionWorkout);
            }
        }
    }
}
