package myprojects.fittdemo.controller;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.dtos.RecordResponseDto;
import myprojects.fittdemo.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecordService recordService;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            final Long memberId = (Long) session.getAttribute("memberId");
            if (memberId != null && memberId >= 0) {
                RecordResponseDto responseDto = recordService.find(memberId, LocalDate.now());
                model.addAttribute("login", true);
                model.addAttribute("record", responseDto);
                return "home";
            }
        }
        return "main";
    }
}
