package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository repository;

    /**
     *
     * @param loginId 사용자ID
     * @param password 비밀번호
     */
    public Member login(String loginId, String password) {
        return repository.findByLoginId(loginId)
                .filter(x -> x.getPassword().equals(password))
                .orElse(null);
    }

}
