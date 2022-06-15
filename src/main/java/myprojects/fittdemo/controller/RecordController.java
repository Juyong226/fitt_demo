package myprojects.fittdemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.RecordResponseDto;
import myprojects.fittdemo.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/records/{dateOfRecord}")
    public String find(HttpServletRequest request,
                       @PathVariable String dateOfRecord, Model model) {
        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute("memberId");
        try {
            RecordResponseDto responseDto = recordService.find(memberId, dateOfRecord);
            model.addAttribute("record", responseDto);
            return "fragments/singleRecord";
        } catch (IllegalStateException e) {
            return "main";
        }
    }
}
