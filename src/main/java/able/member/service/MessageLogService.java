package able.member.service;

import able.member.entity.MessageLog;
import able.member.repository.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageLogService {
    private final MessageLogRepository messageLogRepository;

    public MessageLog addMessageLog(MessageLog authorization) {
        return messageLogRepository.save(authorization);
    }

    public void checkSmsDateTime(MessageLog authorization) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime authorizationDateTime = authorization.getRegDate();

        if (ChronoUnit.MINUTES.between(authorizationDateTime, nowDateTime) < 3) {
            throw new IllegalStateException("문자 재전송은 3분 이후에 가능합니다.");
        }
    }

    public void checkSmsCount(String phoneNumber) {
        Optional<Integer> count = messageLogRepository.countMessageLogByPhoneNumber(phoneNumber);
        if (count.get()>9) {
            throw new IllegalStateException("하루 한도 10회 인증을 초과하였습니다.");
        }
    }

    public Optional<MessageLog> searchTop1ByPhoneNumber(String phoneNumber) {
        return messageLogRepository.searchTop1ByPhoneNumber(phoneNumber);
    }
}
