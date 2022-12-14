package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SessionManager sessionManager;

    /**
     * 메인URL에 들어갔을 때 어떤 URL로 이동시킬지 결정하는 controller
     * <p>session에 따라 결정되며 이때 session이 없는경우 session을 생성하지 않는다.</p>
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model)  {
        // 이때 session을 생성할 의도가 없기 때문에 false로 설정
        // 결국 session도 메모리를 사용하기 때문에 쓸데없이 쓸일이 없는게 좋다
        HttpSession session = request.getSession(false);

        if(session == null) return "home";

        // sessionManager에 저장된 회원정보 조회
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // session에 회원 데이터가 없으면...
        if(loginMember == null) return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}