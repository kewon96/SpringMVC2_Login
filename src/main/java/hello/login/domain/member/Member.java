package hello.login.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class Member {

    /** 임의적으로 부여되는 고유값 */
    private Long id;
    
    /** 사용자ID */
    @NotBlank
    private String loginId;
    
    /** 사용자명 */
    @NotBlank
    private String name;
    
    /** 비밀번호 */
    @NotBlank
    private String password;


}
