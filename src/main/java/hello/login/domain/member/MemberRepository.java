package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /** 저장 */
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member = {}", member);
        store.put(member.getId(), member);

        return member;
    }

    /** id값에 해당하는 사용자 조회 */
    public Member findById(Long id) {
        return store.get(id);
    }

    /** 사용자ID값에 해당하는 사용자 조회 */
    public Optional<Member> findByLoginId(String loginId) {
        List<Member> allList = findAll();

        // 사용자ID값 또한 고유값이기 때문에 findFirst써도 무방
        return allList.stream()
                .filter(x -> x.getLoginId().equals(loginId))
                .findFirst();
    }

    /** 전체 사용자 조회 */
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
