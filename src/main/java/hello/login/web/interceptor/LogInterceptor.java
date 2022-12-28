package hello.login.web.interceptor;

import hello.login.web.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private static final String LOG_ID = "logId";

    /**
     * 요청가기 전
     * @return true : 정상진행 / false : 요청강제종료
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        LogUtils.createMDC(requestURI, uuid);

        // @RequestMapping : HandlerMethod
        // 정적 Resource : ResourceHttpRequestHandler

        if(handler instanceof HandlerMethod) {
            // 호출할 Controller Method의 모든 정보가 포함되어있다.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        return true;
    }

    /**
     * 요청다녀온 후
     * @param modelAndView 요청에서 받아온(반환된) ModelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    /**
     * 최종
     * <p>try~finally의 finally처럼 exception이 발생해도 무조건 타게 된다.</p>
     *
     * @param ex 요청에서 Exception이 발생했다면 해당 Parameter에 담겨있다.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("RESPONSE [{}][{}][{}]", MDC.get("uuid"), MDC.get("requestURI"), handler);

        if(ex != null) log.error("afterCompletion error!!", ex);
    }
}
