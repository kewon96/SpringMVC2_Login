package hello.login.web.filter;

import hello.login.web.SessionConst;
import hello.login.web.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = { "/", "/member/add", "/login", "/logout", "/css/*" };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        LogUtils.createMDC(requestURI, UUID.randomUUID().toString());

        try {
            log.info("인증 확인 필터 시작 {}", requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("인증 확인 Process 실행 {}", requestURI);

                HttpSession session = httpRequest.getSession();

                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    // session이 존재하지 않거나 session 내 로그인사용자관련 정보가 없는 경우...

                    log.info("미인증 사용자 요청 {}", requestURI);

                    // 로그인으로 redirect
                    // 로그인화면으로 보낼 때 그 전에 있던 페이지를 저장
                    // 상품등록 -> 로그인으로 redirect된 상태에서 로그인을 했다면 다시 상품등록으로 가게 하기 위함
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            // 예외에 대한 Log도 가능
            // 단, Tomcat까지 에외를 보내줘야한다.
            throw e;
        } finally {
            log.info("인증 확인 filter 종료");
        }
    }

    /**
     * 로그인확인을 해야하는 경로인지 확인
     *
     * <p>WhiteList를 제외한 모든 경로가 확인되야한다.</p>
     */
    private boolean isLoginCheckPath(String uri) {
        return !PatternMatchUtils.simpleMatch(whiteList, uri);
    }

}
