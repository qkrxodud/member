package abley.member.repository;

import abley.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , UserCustom {

    // 이메일로 유저 찾기
    Optional<User> findByMail(String mail);

    // 유저번호, 이메일로 유저 찾기
    Optional<User> findByUserNoAndMail(Long userNo, String mail);

    // 핸드폰 번호로 유저 찾기
    Optional<User> findByPhoneNumber(String phoneNum);

    // 이메일 + 핸드폰 번호로 유저 찾기
    Optional<User> findByMailAndPhoneNumber(String mail, String phoneNum);

}
