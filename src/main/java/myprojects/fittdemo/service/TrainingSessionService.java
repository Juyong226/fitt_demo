package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.*;
import myprojects.fittdemo.domain.*;
import myprojects.fittdemo.domain.Record;
import myprojects.fittdemo.repository.RecordRepository;
import myprojects.fittdemo.repository.TrainingSessionRepository;
import myprojects.fittdemo.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final RecordRepository recordRepository;
    private final WorkoutRepository workoutRepository;

    /**
     * TrainingSession 단건 조회
     *  - 클라이언트에서 넘어온 sessionWorkoutCount 가 1 이상이면 findWithFetch() 를 호출
     * @param: Long trainingSessionId
     * */
    @Transactional(readOnly = true)
    public TrainingSessionResponseDto find(Integer sessionWorkoutCount, Long trainingSessionId) {
        if (sessionWorkoutCount > 0) {
            return findWithFetch(trainingSessionId);
        }
        TrainingSession findOne = trainingSessionRepository.findOne(trainingSessionId);
        return entityToResponseDto(findOne);
    }

    /**
     * TrainingSession 단건 상세 조회
     *  - 클라이언트에서 넘어온 sessionWorkoutCount 가 1 이상이면 호출
     * @param: Long trainingSessionId
     * @return: TrainingSessionResponseDto
     * */
    @Transactional(readOnly = true)
    public TrainingSessionResponseDto findWithFetch(Long trainingSessionId) {
        TrainingSession findOne = trainingSessionRepository.findWithFetch(trainingSessionId);
        return entityToResponseDto(findOne);
    }

    /**
     * trainingSession 생성 메서드
     * @param: Long recordId, String title
     * @return: TrainingSessionResponseDto
     * */
    public TrainingSessionResponseDto create(Long recordId, String title) {
        Record record = recordRepository.findOne(recordId);
        TrainingSession trainingSession = TrainingSession.create(title);
        trainingSession.relateTo(record);
        trainingSessionRepository.save(trainingSession);
        return new TrainingSessionResponseDto(recordId, trainingSession.getId(), title);
    }

    /**
     * TrainingSession 생성 메서드
     *  - TrainingSession 내부의 SessionWorkout, SessionWorkout 내부의 Round 까지 한번에 저장함
     * @Param: TrainingSessionDto
     * @return: TrainingSessionResponseDto
     * */
    public TrainingSessionResponseDto create(TrainingSessionRequestDto requestDto) {
        Record record = recordRepository.findOne(requestDto.getRecordId());
        TrainingSession trainingSession = convertToEntity(requestDto);
        trainingSession.relateTo(record);
        trainingSessionRepository.save(trainingSession);
        return entityToResponseDto(trainingSession);
    }

    /**
     * TrainingSession 상태 필드 수정 메서드
     * @Param: TrainingSessionDto
     * */
    public void update(Long trainingSessionId, String title) {
        TrainingSession findOne = trainingSessionRepository.findOne(trainingSessionId);
        findOne.update(title);
    }

    /**
     * TrainingSession 삭제 메서드
     * */
    public void remove(Long trainingSessionId) {
        TrainingSession findOne = trainingSessionRepository.findWithFetch(trainingSessionId);
        trainingSessionRepository.remove(findOne);
    }


    //------------------------------------------------------------------------------------------------------------------
    private TrainingSessionResponseDto entityToResponseDto(TrainingSession entity) {
        if (entity == null) return null;
        TrainingSessionResponseDto tsrd = new TrainingSessionResponseDto();
        tsrd.initialize(entity);
        return tsrd;
    }

    private TrainingSession convertToEntity(TrainingSessionRequestDto requestDto) {
        TrainingSession entity = TrainingSession.create(requestDto.getTitle());
        connectEntities(entity, convertToSessionWorkout(requestDto.getSessionWorkoutRequestDtos()));
        return entity;
    }

    private List<SessionWorkout> convertToSessionWorkout(List<SessionWorkoutRequestDto> requestDtos) {
        List<SessionWorkout> entities = new ArrayList<>();
        if (!requestDtos.isEmpty()) {
            for (SessionWorkoutRequestDto requestDto : requestDtos) {
                Workout workout = workoutRepository.findOne(requestDto.getWorkoutId());
                SessionWorkout entity = SessionWorkout.create(workout);
                connectEntities(entity, convertToRounds(requestDto.getRoundRequestDtos()));
                entities.add(entity);
            }
        }
        return entities;
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

    private void connectEntities(TrainingSession trainingSession, List<SessionWorkout> sessionWorkouts) {
        if (!sessionWorkouts.isEmpty()) {
            for (SessionWorkout sessionWorkout : sessionWorkouts) {
                sessionWorkout.relateTo(trainingSession);
            }
        }
    }

    private void connectEntities(SessionWorkout sessionWorkout, List<Round> rounds) {
        if (!rounds.isEmpty()) {
            for (Round round : rounds) {
                round.relateTo(sessionWorkout);
            }
        }
    }
}
