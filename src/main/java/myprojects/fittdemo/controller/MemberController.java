package myprojects.fittdemo.controller;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.controller.dtos.MemberJoinDto;
import myprojects.fittdemo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new MemberJoinDto());
        return "members/joinForm";
    }

//    @GetMapping("/members/{id}")
//    public String memberPage(HttpServletRequest request, @PathVariable final Long id, Model model) {
//        request.getSession()
//    }
}
