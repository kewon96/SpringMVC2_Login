package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * <pre>
     *  세션 생성
     *  1. sessionId 생성
     *  2. sessionStore에 sessionId와 보관할 값 저장
     *  3. 응답객체에 sessionId넣어주기
     * </pre>
     * @param value session에 보관할 데이터
     * @param response 응답
     */
    public void createSession(Object value, HttpServletResponse response) {

        // sessionId를 생성
        String sessionId = UUID.randomUUID().toString();

        // 값을 session에 저장
        sessionStore.put(sessionId, value);

        // Cookie 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        // 응답객체에 cookie넣어주기
        response.addCookie(cookie);


    }

    /** session조회 */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie == null) return null;

        return sessionStore.get(sessionCookie.getValue());
    }

    /** session만료 */
    public void expireSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie == null) return;

        sessionStore.remove(sessionCookie.getValue());
    }

    /**
     * 원하는 Cookie찾기
     * @param request 요청
     * @param cookieName 찾으려는 cookie 이름
     */
    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

}
