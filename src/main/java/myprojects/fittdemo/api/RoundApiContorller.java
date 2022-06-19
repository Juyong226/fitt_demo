package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.RoundRequestDto;
import myprojects.fittdemo.controller.dtos.RoundResponseDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.RoundService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RoundApiContorller {

    private final RoundService roundService;

    @PostMapping("/api/round")
    public Result create(@RequestBody RoundRequestDto requestDto) {
        try {
            requestDto.validation();
            RoundResponseDto responseDto = roundService.create(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("round has been created."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @DeleteMapping("/api/round")
    public Result remove(@RequestParam final Long roundId) {
        try {
            validation(roundId);
            roundService.remove(roundId);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the round has been deleted.")
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
        if (id == null && id <= 0) {
            throw new IllegalStateException("유효하지 않은 Round Id 입니다.");
        }
    }

}
