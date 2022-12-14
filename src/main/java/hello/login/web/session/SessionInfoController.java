package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) return "here haven't session";

        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval()); // session 유효시간(ss)
        log.info("creationTime={}", new Date(session.getCreationTime())); // session이 만들어진 시간
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime())); // 가장 최근에 session접속한 시간
        log.info("isNew={}", session.isNew()); // 새로 생성된 session이냐

        return "printing session";
    }

}
