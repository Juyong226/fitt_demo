package myprojects.fittdemo.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.MemberJoinDto;
import myprojects.fittdemo.controller.dtos.MemberRequestDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members/validate")
    public Result validate(HttpServletRequest request) {
        try {
            memberService.validateMember(request.getParameter("nickname"));
            return new Result(new ExceptionDto(false), "사용 가능한 닉네임입니다.");
        } catch (IllegalStateException e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, e.getMessage());
        } catch (Exception e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, "서버에 문제가 발생했습니다. 잠시후 다시 시도해주세요.");
        }
    }

    @PostMapping("/api/members/join")
    public Result join(MemberJoinDto joinDto) {
        memberService.join(joinDto);
        return new Result(new ExceptionDto(false), "/");
    }

    @PostMapping("/api/members/login")
    public Result login(HttpServletRequest request, MemberRequestDto requestDto) {
        try {
            Long memberId = memberService.login(requestDto);
            HttpSession session = request.getSession();
            session.setAttribute("memberId", memberId);
            return new Result(new ExceptionDto(false), "/");
        } catch (IllegalStateException e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, e.getMessage());
        } catch (Exception e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, e.getMessage());
        }
    }

    @Data
    static class Result<T> {
        private ExceptionDto exception;
        private T result;

        public Result(ExceptionDto exception) {
            setException(exception);
        }

        public Result(ExceptionDto exception, T result) {
            this(exception);
            setResult(result);
        }
    }
}

