package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    /**
     * 메인URL에 들어갔을 때 어떤 URL로 이동시킬지 결정하는 controller
     * @param loginMember session에 담아있는 Member객체 / @SessionAttribute로 인해 session이 새로 생성될 일은 없다.
     * @param model
     */
    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model)  {
        if(loginMember == null) return "home";

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}