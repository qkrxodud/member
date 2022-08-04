package able.member.service;

import able.member.dto.UserLoginResponseDto;
import able.member.entity.User;
import able.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 로그인
    public User login(String mail, String password) {
        User user = userRepository.findByMail(mail).orElseThrow(IllegalArgumentException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) throw  new IllegalStateException();
        return user;
    }

    //회원가입
    public User join(User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }
}
