package able.member.service;

import able.member.entity.Authorization;
import able.member.entity.StatusValue;
import able.member.repository.AuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    public Authorization addAuthorization(Authorization authorization) {
        return authorizationRepository.save(authorization);
    }

    @Transactional
    public Boolean checkRandomNumber(String phoneNum, String randomNum, String messageRandomNum) {
        Authorization authorization = findByPhoneNumber(phoneNum)
                .orElseThrow(() -> new IllegalStateException("핸드폰 번호를 확인해 주세요."));

        if (messageRandomNum.equals(randomNum)) {
            authorization.changePhoneCheckValue(StatusValue.Y);
            return true;
        }

        return false;
    }

    public Optional<Authorization> findByPhoneNumber(String phoneNum) {
        return authorizationRepository.findByPhoneNumber(phoneNum);
    }

    public Boolean existsByPhoneNumber(String phoneNumber) {
        return authorizationRepository.existsByPhoneNumber(phoneNumber);
    }

}
