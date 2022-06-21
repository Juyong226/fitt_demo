package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.WorkoutRequestDto;
import myprojects.fittdemo.controller.dtos.WorkoutResponseDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WorkoutApiController {

    private final WorkoutService workoutService;

    @GetMapping("/api/workout")
    public Result find(@RequestBody WorkoutRequestDto requestDto) {
        List<WorkoutResponseDto> responseDtoList = workoutService.findAll(requestDto);
        return new Result(
                new ExceptionDto(false),
                new MessageAndRedirection("workout list for the muscle categories requested."),
                responseDtoList
        );
    }

    @PostMapping("/api/workout")
    public Result create(@RequestBody WorkoutRequestDto requestDto) {
        try {
            requestDto.validation();
            WorkoutResponseDto responseDto = workoutService.create(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the workout has been created."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @PutMapping("/api/workout")
    public Result update(@RequestBody WorkoutRequestDto requestDto) {
        try {
            if (requestDto.validation() == 1) {
                throw new IllegalStateException("유효한 workout Id 값이 아닙니다.");
            }
            WorkoutResponseDto responseDto = workoutService.update(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the workout has been updated."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @DeleteMapping("/api/workout")
    public Result remove(@RequestParam final Long workoutId) {
        try {
            validation(workoutId);
            workoutService.remove(workoutId);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the workout has been deleted.")
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void validation(Long id) throws IllegalStateException {
        if (id == null || id <= 0) {
            throw new IllegalStateException("유효한 Id 값이 아닙니다.");
        }
    }
}
