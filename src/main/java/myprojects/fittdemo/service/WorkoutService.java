package myprojects.fittdemo.service;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.WorkoutRequestDto;
import myprojects.fittdemo.controller.WorkoutResponseDto;
import myprojects.fittdemo.domain.MuscleCategory;
import myprojects.fittdemo.domain.Workout;
import myprojects.fittdemo.domain.WorkoutMuscleCategory;
import myprojects.fittdemo.repository.MuscleCategoryRepository;
import myprojects.fittdemo.repository.WorkoutMuscleCategoryRepository;
import myprojects.fittdemo.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MuscleCategoryRepository muscleCategoryRepository;
    private final WorkoutMuscleCategoryRepository workoutMuscleCategoryRepository;

    /**
     * Workout 단건 생성 메서드
     *  - 생성할 Workout 이름, 속할 MuscleCategory 정보를 파라미터로 전달 받음
     *  - 새로운 Workout 을 생성함
     *  - 전달 받은 MuscleCategory Id 리스트로 MuscleCategory 를 조회함
     *  - 조회된 MuscleCategory 의 수만큼 for 문을 돌며 WorkoutMuscleCategory 를 생성하고,
     *    이를 Workout, MuscleCategory 객체와 연관 관계를 맺어줌
     *  - 새로운 Workout 을 persist 하고, 이를 Dto 로 변환하여 반환
     * @param: WorkoutRequestDto
     * @return: WorkoutResponseDto
     * */
    public WorkoutResponseDto create(WorkoutRequestDto param) {
        List<MuscleCategory> muscleCategories =
                muscleCategoryRepository.findByIds(param.getMuscleCategoryIds());
        Workout workout = Workout.create(param.getWorkoutName());
        workout.addMuscleCategories(createWorkoutMuscleCategories(workout, muscleCategories));
        workoutRepository.save(workout);
        return entityToResponseDto(workout);
    }

    /**
     * Workout 조회 메서드
     *  - MuscleCategory Id 리스트를 파라미터로 전달받아 해당 카테고리에 포함되는 Workout 리스트를 조회 후 Dto로 변환하여 반환
     * @param: WorkoutRequestDto
     * @return: WorkoutResponseDto
     * */
    @Transactional(readOnly = true)
    public WorkoutResponseDto find(Long workoutId) {
        Workout findOne = workoutRepository.findOne(workoutId);
        return entityToResponseDto(findOne);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDto> findAll(WorkoutRequestDto requestDto) {
        List<Long> muscleCategoryIds = requestDto.getMuscleCategoryIds();
        if (!muscleCategoryIds.isEmpty()) {
            return findByMuscleCategories(muscleCategoryIds);
        }
        List<Workout> workouts = workoutRepository.findAll();
        return entitiesToResponseDtos(workouts);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDto> findByMuscleCategories(List<Long> muscleCategoryIds) {
        List<Workout> workouts = workoutRepository.findByMuscleCategories(muscleCategoryIds);
        List<Workout> result = filterByMuscleCategories(workouts, muscleCategoryIds);
        return entitiesToResponseDtos(result);
    }

    /**
     * Workout 수정 메서드
     *  - 수정할 Workout 의 name, 연관된 MuscleCategory 정보를 파라미터로 전달받아 기존의 값을 대체한다.
     * @Param: WorkoutRequestDto
     * @return: WorkoutResponseDto
     * */
    public WorkoutResponseDto update(WorkoutRequestDto requestDto) {
        Workout findOne = workoutRepository.findOne(requestDto.getWorkoutId());
        List<MuscleCategory> muscleCategories =
                muscleCategoryRepository.findByIds(requestDto.getMuscleCategoryIds());
        findOne.update(requestDto.getWorkoutName());
        List<WorkoutMuscleCategory> created = createWorkoutMuscleCategories(findOne, muscleCategories);
        for (WorkoutMuscleCategory wmc : created) {
            workoutMuscleCategoryRepository.save(wmc);
        }
        return entityToResponseDto(findOne);
    }

    /**
     * Workout 삭제 메서드
     *  - Workout Id 를 파라미터로 전달받아 엔티티 조회 후 삭제한다.
     * @param: Long workoutId
     */
    public void remove(Long workoutId) {
        Workout findOne = workoutRepository.findOne(workoutId);
        workoutRepository.remove(findOne);
    }

    //------------------------------------------------------------------------------------------------------------------

    private List<WorkoutResponseDto> entitiesToResponseDtos(List<Workout> entities) {
        List<WorkoutResponseDto> responseDtos = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (Workout workout : entities) {
                responseDtos.add(entityToResponseDto(workout));
            }
        }
        return responseDtos;
    }
    private WorkoutResponseDto entityToResponseDto(Workout entity) {
        WorkoutResponseDto workoutResponseDto = new WorkoutResponseDto();
        workoutResponseDto.initialize(entity);
        return workoutResponseDto;
    }

    private List<Workout> filterByMuscleCategories(List<Workout> workouts, List<Long> muscleCategoryIds) {
        /*
         * ConcurrentModificationException 발생 지점
         *   - enhanced for loop 을 통해 컬렉션을 조작하면 발생 (나의 경우 List.remove(element))
         *   - enhanced for loop 가 iterator 를 통해 동작하는 것과 관련
         *   - iterator.next() 호출 시 수행되는 checkForComodifiaction() 에서
         *       loop 수행 중인 컬렉션에 조작(삽입, 삭제)이 확인되면 해당 예외를 발생시킴
         *   - loop 수행 중 해당 컬렉션 내 모든 원소에 접근할 수 있는 보장이 없어지기 때문에 예외를 발생시키는 것으로 짐작
         *       그래서 동시성 문제와 연관된 예외일 것으로 생각됨(여러 쓰레드에서 같은 컬렉션을 조작할 경우)
         *   - java.util.concurrent 패키지 내 컬렉션을 활용하여 해결 가능
         *   - 여기선 stream 을 활용
         * */
        return workouts.stream()
                .filter(workout -> workout.hasRelationsWith(muscleCategoryIds))
                .collect(Collectors.toList());
    }

    private List<WorkoutMuscleCategory> createWorkoutMuscleCategories(Workout workout,
                                                                      List<MuscleCategory> muscleCategories) {
        List<WorkoutMuscleCategory> workoutMuscleCategories = new ArrayList<>();
        for (MuscleCategory muscleCategory : muscleCategories) {
            WorkoutMuscleCategory workoutMuscleCategory =
                    WorkoutMuscleCategory.create(workout, muscleCategory);
            workoutMuscleCategories.add(workoutMuscleCategory);
        }
        return workoutMuscleCategories;
    }
}
