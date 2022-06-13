package myprojects.fittdemo.api;

import lombok.extern.slf4j.Slf4j;
import myprojects.fittdemo.controller.dtos.exception.ExceptionDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class SessionApiController {

    @GetMapping("/api/deleteSession")
    public Result deleteSession(HttpServletRequest request) {
        log.info("[SessionApiController]: deleting session has started.");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("[SessionApiController]: session has deleted.");
            return new Result(new ExceptionDto(false),
                    new MessageAndRedirection("세션이 삭제되었습니다.","/"));
        }
        log.info("[SessionApiController]: no valid session exist.");
        return new Result(new ExceptionDto(false),
                new MessageAndRedirection("세션이 존재하지 않습니다.", "/"));
    }
}
