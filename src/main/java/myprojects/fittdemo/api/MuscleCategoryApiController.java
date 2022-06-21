package myprojects.fittdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.domain.MuscleCategory;
import myprojects.fittdemo.repository.MuscleCategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MuscleCategoryApiController {

    private final MuscleCategoryRepository muscleCategoryRepository;

    @GetMapping("/api/mc")
    public Result getCategories() {
        List<MuscleCategory> muscleCategories = muscleCategoryRepository.findAll();
        return new Result(
                new ExceptionDto(false),
                new MessageAndRedirection("all the muscle categories has been read."),
                muscleCategories
        );
    }
}
