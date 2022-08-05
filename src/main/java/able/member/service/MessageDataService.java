package able.member.service;

import able.member.entity.ConfirmLog;
import able.member.repository.ConfirmLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageDataService {
    private final ConfirmLogRepository authorizationRepository;

    public ConfirmLog addAuthorization(ConfirmLog authorization) {
        return authorizationRepository.save(authorization);
    }

    public void checkSmsDateTime(ConfirmLog authorization) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime authorizationDateTime = authorization.getRegDate();

        if (ChronoUnit.MINUTES.between(authorizationDateTime, nowDateTime) < 3) {
            throw new IllegalStateException("문자 재전송은 3분 이후에 가능합니다.");
        }
    }

    public void checkSmsCount(String phoneNumber) {
        Optional<Integer> count = authorizationRepository.findCountAuthorization(phoneNumber);
        if (count.get()>9) {
            throw new IllegalStateException("하루 한도 10회 인증을 초과하였습니다.");
        }
    }

    public Optional<ConfirmLog> findTop1ByAuthorization(String phoneNumber) {
        return authorizationRepository.findTop1ByAuthorization(phoneNumber);
    }
}
