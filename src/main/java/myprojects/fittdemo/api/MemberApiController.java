package myprojects.fittdemo.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.MemberJoinDto;
import myprojects.fittdemo.controller.dtos.MemberRequestDto;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import myprojects.fittdemo.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Result memberValidation(HttpServletRequest request) {
        try {
            memberService.validateMember(request.getParameter("nickname"));
            return new Result(new ExceptionDto(false), new MessageAndRedirection("사용 가능한 닉네임입니다."));
        } catch (IllegalStateException e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, new MessageAndRedirection(e.getMessage()));
        } catch (Exception e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, new MessageAndRedirection("서버에 문제가 발생했습니다. 잠시후 다시 시도해주세요."));
        }
    }

    @PostMapping("/api/members/join")
    public Result join(MemberJoinDto joinDto) {
        memberService.join(joinDto);
        return new Result(new ExceptionDto(false), new MessageAndRedirection("회원가입 되었습니다.", "/"));
    }

    @PostMapping("/api/members/login")
    public Result login(HttpServletRequest request, MemberRequestDto requestDto) {
        try {
            Long memberId = memberService.login(requestDto);
            HttpSession session = request.getSession();
            session.setAttribute("memberId", memberId);
            return new Result(new ExceptionDto(false), new MessageAndRedirection("로그인 되었습니다.", "/"));
        } catch (IllegalStateException e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, new MessageAndRedirection(e.getMessage()));
        } catch (Exception e) {
            ExceptionDto exception = new ExceptionDto(true, e.getMessage());
            return new Result(exception, new MessageAndRedirection(e.getMessage()));
        }
    }

    @GetMapping("/api/members/logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return new Result(new ExceptionDto(false), new MessageAndRedirection("로그아웃 되었습니다.", "/"));
        }
        return new Result(new ExceptionDto(false), new MessageAndRedirection("세션이 만료되었습니다.", "/"));
    }

    @GetMapping("/api/members/session-validation")
    public Result sessionValidation(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new Result(new ExceptionDto(false), new MessageAndRedirection("세션이 만료되었습니다.", "/"));
        }
        return new Result(new ExceptionDto(false), new MessageAndRedirection("세션이 유지되고 있습니다."));
    }

    @Data
    static class Result<T> {
        private ExceptionDto exception;
        private MessageAndRedirection messageAndRedirection;
        private String message;
        private T result;

        public Result(ExceptionDto exception) {
            setException(exception);
        }

        public Result(ExceptionDto exception, MessageAndRedirection messageAndRedirection) {
            this(exception);
            setMessageAndRedirection(messageAndRedirection);
        }

        public Result(ExceptionDto exception, MessageAndRedirection messageAndRedirection, T result) {
            this(exception, messageAndRedirection);
            setResult(result);
        }
    }

    @Data
    static class MessageAndRedirection {
        private String message;
        private String url;

        public MessageAndRedirection(String message) {
            setMessage(message);
        }

        public MessageAndRedirection(String message, String url) {
            this(message);
            setUrl(url);
        }
    }
}

