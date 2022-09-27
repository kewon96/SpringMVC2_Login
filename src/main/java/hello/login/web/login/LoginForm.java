package hello.login.web.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/** 로그인Form에서 입력한 값 객체 */
@Getter @Setter
public class LoginForm {

    /** 사용자ID */
    @NotBlank
    private String loginId;

    /** 비밀번호 */
    @NotBlank
    private String password;

}
