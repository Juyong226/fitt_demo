package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.SessionWorkoutRequestDto;
import myprojects.fittdemo.controller.dtos.SessionWorkoutResponseDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.SessionWorkoutService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SessionWorkoutApiController {

    private final SessionWorkoutService sessionWorkoutService;

    @PostMapping("/api/sw")
    public Result create(@RequestBody SessionWorkoutRequestDto requestDto) {
        try {
            requestDto.validation();
            SessionWorkoutResponseDto responseDto = sessionWorkoutService.create(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("SessionWorkout has created."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @PutMapping("/api/sw")
    public Result update(@RequestBody SessionWorkoutRequestDto requestDto) {
        try {
            if (requestDto.validation() == 0) {
                throw new IllegalStateException("유효한 SessionWorkout Id 값이 아닙니다.");
            }
            SessionWorkoutResponseDto responseDto = sessionWorkoutService.update(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("SessionWorkout has updated."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @DeleteMapping("/api/sw")
    public Result remove(@RequestParam final Long sessionWorkoutId) {
        try {
            validation(sessionWorkoutId);
            sessionWorkoutService.remove(sessionWorkoutId);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("SessionWorkout has deleted.")
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void validation(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalStateException("유효한 Id 값이 아닙니다.");
        }
    }
}
