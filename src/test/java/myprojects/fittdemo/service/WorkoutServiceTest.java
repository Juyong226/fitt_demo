package myprojects.fittdemo.service;

import myprojects.fittdemo.controller.dtos.WorkoutRequestDto;
import myprojects.fittdemo.controller.dtos.WorkoutResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WorkoutServiceTest {

    @Autowired
    WorkoutService workoutService;

    @Test
    @Rollback(value = false)
    public void create_test() {
        // given
        String name = "사이드 레터럴 레이즈";

        List<Long> muscleCategoryIds = new ArrayList<>();
        muscleCategoryIds.add(3L);
        muscleCategoryIds.add(9L);
        muscleCategoryIds.add(11L);

        WorkoutRequestDto requestDto = new WorkoutRequestDto();
        requestDto.setWorkoutName(name);
        requestDto.setMuscleCategoryIds(muscleCategoryIds);

        // when
        WorkoutResponseDto responseDto = workoutService.create(requestDto);
        WorkoutResponseDto result = workoutService.find(responseDto.getWorkoutId());

        // then
        assertEquals(responseDto.getWorkoutId(), result.getWorkoutId());
        assertEquals(name, result.getWorkoutName());
    }

    @Test
    public void find_all_test() {
        // given
        List<Long> muscleCategoryIds = new ArrayList<>();
        WorkoutRequestDto requestDto = new WorkoutRequestDto();
        requestDto.setMuscleCategoryIds(muscleCategoryIds);

        // when
        List<WorkoutResponseDto> result = workoutService.findAll(requestDto);

        // then (클래스 단위로 테스트 할 시 expected 값을 14로 변경해야 성공)
        assertEquals(13, result.size());
    }

    @Test
    public void find_all_with_muscle_categories() {
        // given
        List<Long> muscleCategoryIds = new ArrayList<>();
        muscleCategoryIds.add(9L);
        muscleCategoryIds.add(11L);

        WorkoutRequestDto requestDto = new WorkoutRequestDto();
        requestDto.setMuscleCategoryIds(muscleCategoryIds);

        // when
        List<WorkoutResponseDto> result = workoutService.findAll(requestDto);

        // then
        assertNotEquals(13, result.size());
        assertEquals(4, result.size());
    }

    @Test
    @Rollback(value = false)
    public void update_test() {
        // given
        Long workoutId = 1L;
        String workoutName = "lat pull down";
        List<Long> muscleCategoryIds = new ArrayList<>();
        muscleCategoryIds.add(2L);

        WorkoutRequestDto requestDto = new WorkoutRequestDto();
        requestDto.setWorkoutId(workoutId);
        requestDto.setWorkoutName(workoutName);
        requestDto.setMuscleCategoryIds(muscleCategoryIds);

        WorkoutResponseDto findOne = workoutService.find(workoutId);

        // when
        WorkoutResponseDto result = workoutService.update(requestDto);

        // then
        assertEquals(findOne.getWorkoutId(), result.getWorkoutId());
        assertEquals(workoutName, result.getWorkoutName());
        assertEquals(muscleCategoryIds.size(), result.getTargetMuscles().size());
    }


    @Test
    public void remove_test() {
        // given
        Long workoutId = 1L;

        // when
        workoutService.remove(workoutId);
        WorkoutResponseDto result = workoutService.find(workoutId);

        // then
        assertEquals(null, result);
    }
}