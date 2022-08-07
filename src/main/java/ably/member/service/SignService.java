package ably.member.service;

import ably.member.entity.User;
import ably.member.exhandler.exception.CEmailLoginFailedException;
import ably.member.exhandler.exception.CPhoneLoginFailedException;
import ably.member.exhandler.exception.CUserNotFoundException;
import ably.member.repository.UserRepository;
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

    //이메일 로그인
    public User loginEmail(String mail, String password) {
        User user = userRepository.findByMail(mail)
                .orElseThrow(() -> new CUserNotFoundException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw  new CEmailLoginFailedException();
        }
        return user;
    }
    //핸드폰 번호 로그인
    public User loginPhone(String phoneNum, String password) {
        User user = userRepository.findByPhoneNumber(phoneNum)
                .orElseThrow(() -> new CUserNotFoundException());

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw  new CPhoneLoginFailedException();
        }
        return user;
    }

    //회원가입
    @Transactional
    public User join(User user) {
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    // 회원 정보 업데이트
    @Transactional
    public void update(String mail, String phoneNum, String password) {
        User user = userRepository.findByMailAndPhoneNumber(mail, phoneNum)
                .orElseThrow(CUserNotFoundException::new);

        user.changePassword(passwordEncoder.encode(password));
    }
}
