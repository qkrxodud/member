package ably.member.service;

import ably.member.entity.User;
import ably.member.exhandler.exception.CUserDuplicationException;
import ably.member.exhandler.exception.CUserNotFoundException;
import ably.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

     // 회원 찾기
    public User findUserByUserNoAndMail(Long userNo, String mail) {
        User user = userRepository.findByUserNoAndMail(userNo, mail)
                .orElseThrow(CUserNotFoundException::new);

        return user;
    }

    // 회원 확인
    public void validateDuplicateMember(String mail, String phoneNumber) {
        Optional<User> checkMail = userRepository.existsDynamicUserQuery(mail,"");
        Optional<User> checkPhone = userRepository.existsDynamicUserQuery("",phoneNumber);
        if (!checkMail.isEmpty() || !checkPhone.isEmpty()) {
            throw new CUserDuplicationException();
        }
    }

}
