package able.member.repository;

import able.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이름으로 찾기
    Optional<User> findByName(String name);

    // 이메일로 찾기
    Optional<User> findByMail(String mail);

    // 이메일로 찾기
    Optional<User> findByPhoneNumber(String phoneNum);
}
