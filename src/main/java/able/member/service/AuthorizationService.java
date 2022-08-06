package able.member.service;

import able.member.entity.Authorization;
import able.member.entity.StatusValue;
import able.member.exhandler.exception.CAuthenticationEntryPointException;
import able.member.exhandler.exception.CAuthorizationNotFoundException;
import able.member.exhandler.exception.CUserNotFoundException;
import able.member.repository.AuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Authorization authorization = findByPhoneNumber(phoneNum);
        if (messageRandomNum.equals(randomNum)) {
            authorization.changePhoneCheckValue(StatusValue.Y);
            return true;
        }
        return false;
    }

    public Authorization findByPhoneNumber(String phoneNum) {
        return authorizationRepository.findByPhoneNumber(phoneNum)
                .orElseThrow(CAuthorizationNotFoundException::new);
    }

    @Transactional
    public void initAuthorization(String phoneNum) {
        Authorization authorization = findByPhoneNumber(phoneNum);
        authorization.changePhoneCheckValue(StatusValue.N);
    }

    public Boolean existsByPhoneNumber(String phoneNumber) {
        return authorizationRepository.existsByPhoneNumber(phoneNumber);
    }

}
