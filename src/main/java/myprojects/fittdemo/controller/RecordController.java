package myprojects.fittdemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.RecordRequestDto;
import myprojects.fittdemo.controller.dtos.RecordResponseDto;
import myprojects.fittdemo.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/records/{dateOfRecord}")
    public String find(HttpServletRequest request,
                       @PathVariable final String dateOfRecord, Model model) {
        HttpSession session = request.getSession(false);
        final Long memberId = (Long) session.getAttribute("memberId");
        try {
            RecordResponseDto responseDto = recordService.find(memberId, dateOfRecord);
            model.addAttribute("record", responseDto);
            return "fragments/singleRecord";
        } catch (IllegalStateException e) {
            return "main";
        }
    }

    @PostMapping("/records/{dateOfRecord}")
    public String create(HttpServletRequest request,
                         @PathVariable final String dateOfRecord, Model model) {
        HttpSession session = request.getSession(false);
        final Long memberId = (Long) session.getAttribute("memberId");
        try {
            RecordResponseDto responseDto = recordService.create(memberId, dateOfRecord);
            model.addAttribute("record", responseDto);
            return "fragments/singleRecord";
        } catch (IllegalStateException e) {
            return "main";
        }
    }

    @GetMapping("/records/{recordId}/update-form")
    public String updateForm(HttpServletRequest request,
                             @PathVariable final Long recordId, Model model) {
        RecordResponseDto responseDto = recordService.find(recordId);
        model.addAttribute("record", responseDto);
        return "fragments/recordUpdateForm";
    }

    @PutMapping("/records/{recordId}")
    public String update(HttpServletRequest request,
                         RecordRequestDto requestDto, Model model) {
        HttpSession session = request.getSession(false);
        final Long memberId = (Long) session.getAttribute("memberId");
        RecordResponseDto responseDto = recordService.update(requestDto);
        model.addAttribute("record", responseDto);
        return "fragments/singleRecord";
    }

    @DeleteMapping("/records/{recordId}")
    public String remove(HttpServletRequest request,
                         @PathVariable final Long recordId, Model model) {
        RecordResponseDto responseDto = recordService.remove(recordId);
        model.addAttribute("record", responseDto);
        return "fragments/singleRecord";
    }
}
