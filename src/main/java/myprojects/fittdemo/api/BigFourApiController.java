package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.BigFourRequestDto;
import myprojects.fittdemo.controller.dtos.BigFourResonseDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.BigFourService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BigFourApiController {

    private BigFourService bigFourService;

    @PostMapping("/api/bigFour")
    public Result create(BigFourRequestDto requestDto) {
        try {
            requestDto.validate();
            BigFourResonseDto responseDto = bigFourService.create(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("new bigFour has been created."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @PutMapping("/api/bigFour")
    public Result update(BigFourRequestDto requestDto) {
        try {
            if (requestDto.validate() == 1) {
                throw new IllegalStateException("유효한 bigFour Id 값이 아닙니다.");
            }
            BigFourResonseDto responseDto = bigFourService.update(requestDto);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the bigFour has been updated."),
                    responseDto
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    @DeleteMapping("/api/bigFour")
    public Result remove(@RequestParam final Long bigFourId) {
        try {
            validate(bigFourId);
            bigFourService.remove(bigFourId);
            return new Result(
                    new ExceptionDto(false),
                    new MessageAndRedirection("the bigFour has been deleted.")
            );
        } catch (IllegalStateException e) {
            return new Result(
                    new ExceptionDto(true, e.getMessage()),
                    new MessageAndRedirection(e.getMessage())
            );
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private void validate(Long id) throws IllegalStateException {
        if (id == null || id <= 0) {
            throw new IllegalStateException("유효한 bigFour Id 값이 아닙니다.");
        }
    }
}
