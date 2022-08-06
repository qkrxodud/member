package able.member.service;

import able.member.entity.MessageLog;
import able.member.exhandler.exception.CMessageSendCountOverException;
import able.member.exhandler.exception.CMessageSendFailedException;
import able.member.exhandler.exception.CMessageSendTimeOverException;
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

    public void validationCheck(String phoneNumber, MessageLog messageLog) {
        // 하루 전송량 10회
        checkSmsCount(phoneNumber);
        // 3분 이내에 시간 체크
        checkSmsDateTime(messageLog);
    }

    public void checkSmsDateTime(MessageLog authorization) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime authorizationDateTime = authorization.getRegDate();

        if (ChronoUnit.MINUTES.between(authorizationDateTime, nowDateTime) < 2) {
            throw new CMessageSendTimeOverException();
        }
    }

    public void checkSmsCount(String phoneNumber) {
        Optional<Integer> count = messageLogRepository.countMessageLogByPhoneNumber(phoneNumber);
        if (count.get()>9) {
            throw new CMessageSendCountOverException();
        }
    }

    public Optional<MessageLog> searchTop1ByPhoneNumber(String phoneNumber) {
        return messageLogRepository.searchTop1ByPhoneNumber(phoneNumber);
    }
}