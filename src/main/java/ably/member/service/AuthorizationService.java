package ably.member.service;

import ably.member.entity.Authorization;
import ably.member.entity.StatusValue;
import ably.member.exhandler.exception.CAuthorizationNotFoundException;
import ably.member.exhandler.exception.CMessageCheckFailedException;
import ably.member.repository.AuthorizationRepository;
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

    // 랜덤 번호 체크
    @Transactional
    public Boolean checkRandomNumber(String mail, String phoneNum, String randomNum, String messageRandomNum) {
        Authorization authorization = findByMailAndPhoneNumber(mail, phoneNum);
        if (messageRandomNum.equals(randomNum)) {
            authorization.changePhoneCheckValue(StatusValue.Y);
            return true;
        }
        throw new CMessageCheckFailedException();
    }

    // 권한 찾기기
   public Authorization findByMailAndPhoneNumber(String mail, String phoneNum) {
        return authorizationRepository.findByMailAndPhoneNumber(mail, phoneNum)
                .orElseThrow(CAuthorizationNotFoundException::new);
    }

    // 권한 초기화
    @Transactional
    public void initAuthorization(String mail, String phoneNum) {
        Authorization authorization = findByMailAndPhoneNumber(mail, phoneNum);
        authorization.changePhoneCheckValue(StatusValue.N);
    }

    public Boolean existsByPhoneNumber(String phoneNumber) {
        return authorizationRepository.existsByPhoneNumber(phoneNumber);
    }

}
