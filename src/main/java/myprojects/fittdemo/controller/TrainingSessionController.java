package myprojects.fittdemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.TrainingSessionResponseDto;
import myprojects.fittdemo.controller.dtos.TrainingSessionSimpleDto;
import myprojects.fittdemo.service.TrainingSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @GetMapping("/tss/title-form")
    public String titleForm(Model model) {
        model.addAttribute("titleForm", new TrainingSessionSimpleDto());
        return "fragments/trainingSessionTitleForm";
    }

    @PostMapping("/tss")
    public String create(final Long recordId, final String title, Model model) {
        TrainingSessionSimpleDto responseDto = trainingSessionService.create(recordId, title);
        model.addAttribute("trainingSession", responseDto);
        return "fragments/trainingSessionPreview";
    }
}
