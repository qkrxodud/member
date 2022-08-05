package able.member.service;

import able.member.entity.User;
import able.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

     // 회원 찾기
    public User findUserByMail(String mail) {
        User user = userRepository.findByMail(mail).orElseThrow(IllegalArgumentException::new);
        return user;
    }

}
