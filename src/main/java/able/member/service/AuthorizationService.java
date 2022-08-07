package able.member.service;

import able.member.entity.Authorization;
import able.member.entity.StatusValue;
import able.member.exhandler.exception.CAuthenticationEntryPointException;
import able.member.exhandler.exception.CAuthorizationNotFoundException;
import able.member.exhandler.exception.CMessageCheckFailedException;
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
    public Boolean checkRandomNumber(String mail, String phoneNum, String randomNum, String messageRandomNum) {
        Authorization authorization = findByMailAndPhoneNumber(mail, phoneNum);
        if (messageRandomNum.equals(randomNum)) {
            authorization.changePhoneCheckValue(StatusValue.Y);
            return true;
        }
        throw new CMessageCheckFailedException();
    }

    public Authorization findByMailAndPhoneNumber(String mail, String phoneNum) {
        return authorizationRepository.findByMailAndPhoneNumber(mail, phoneNum)
                .orElseThrow(CAuthorizationNotFoundException::new);
    }

    @Transactional
    public void initAuthorization(String mail, String phoneNum) {
        Authorization authorization = findByMailAndPhoneNumber(mail, phoneNum);  //등록된 회원이 없습니다.
        authorization.changePhoneCheckValue(StatusValue.N);
    }

    public Boolean existsByPhoneNumber(String phoneNumber) {
        return authorizationRepository.existsByPhoneNumber(phoneNumber);
    }

}
