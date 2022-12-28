package hello.login.web.argument_resolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * ArgumentResolver 발동 조건(Parameter기준)
     *
     * <pre>
     * 1. Login Annotation이 붙은 Parameter인지
     * 2. Parameter Type이 Member인지
     * </pre>
     *
     * 내부적으로 캐시가 존재해 한번 호출한 이후로는 호출하지않는다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        // Login Annotation이 붙은 Parameter인지
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        
        // Parameter Type이 Member인지
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // 둘 다 해당되어야는 조건
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 이때 단순히 로그인하러 온 경우일 수도 있어 새로 session이 만들어지는 것을 비활성화한다.
        HttpSession session = request.getSession(false);

        if(session == null) return null;

        // supportsParameter에 만족하는 Parameter에 LoginMember를 이식한다.
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
