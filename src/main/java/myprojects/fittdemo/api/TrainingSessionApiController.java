package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.TrainingSessionResponseDto;
import myprojects.fittdemo.controller.dtos.TrainingSessionSimpleDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.TrainingSessionService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TrainingSessionApiController {

    private final TrainingSessionService trainingSessionService;

    @PostMapping("/api/tss")
    public Result create(@RequestParam final Long recordId,
                         @RequestParam final String title) {
        try {
            validation(recordId, title);
            TrainingSessionSimpleDto responseDto = trainingSessionService.create(recordId, title);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("운동 세션이 생성되었습니다."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @GetMapping("/api/tss/{trainingSessionId}")
    public Result readDetail(@PathVariable final Long trainingSessionId,
                             @RequestParam final Integer sessionWorkoutCount) {
        try {
            validation(trainingSessionId);
            TrainingSessionResponseDto responseDto =
                    trainingSessionService.find(trainingSessionId, sessionWorkoutCount);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("운동 세션이 조회되었습니다."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @PutMapping("/api/tss/{trainingSessionId}")
    public Result update(@PathVariable final Long trainingSessionId,
                         @RequestParam final String title) {
        try {
            validation(trainingSessionId, title);
            TrainingSessionResponseDto responseDto =
                    trainingSessionService.update(trainingSessionId, title);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("운동 세션 제목이 변경되었습니다."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @DeleteMapping("/api/tss/{trainingSessionId}")
    public Result remove(@PathVariable final Long trainingSessionId) {
        try {
            validation(trainingSessionId);
            trainingSessionService.remove(trainingSessionId);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("운동 세션이 삭제되었습니다.")
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

    private void validation(Long id, String title) {
        validation(id);
        if (title == null || title.equals(" ")) {
            throw new IllegalStateException("유효한 Title 값이 아닙니다.");
        }
    }
}
