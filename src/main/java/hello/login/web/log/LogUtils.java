package hello.login.web.log;

import org.slf4j.MDC;

public class LogUtils {

    public static void createMDC(String requestURI, String uuid) {
        MDC.put("uuid", uuid);
        MDC.put("requestURI", requestURI);
    }

}
