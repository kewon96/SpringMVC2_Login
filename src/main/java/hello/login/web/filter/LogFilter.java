package hello.login.web.filter;

import hello.login.web.log.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        LogUtils.createMDC(requestURI, uuid);

        try {
            log.info("REQUEST [{}] / [{}]", MDC.get("uuid"), MDC.get("requestURI"));
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}] / [{}]", MDC.get("uuid"), MDC.get("requestURI"));
            MDC.clear();
        }

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
