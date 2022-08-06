package able.member.apiController;

import able.member.entity.Authorization;
import able.member.entity.MessageLog;
import able.member.entity.StatusValue;
import able.member.exhandler.exception.CMessageCheckFailedException;
import able.member.exhandler.exception.CMessageSendFailedException;
import able.member.exhandler.exception.CUserNotFoundException;
import able.member.model.response.SingleResult;
import able.member.service.AuthorizationService;
import able.member.service.MessageLogService;
import able.member.service.MessageService;
import able.member.service.ResponseService;
import able.member.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.el.MethodNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageLogService messageLogService;
    private final AuthorizationService authorizationService;
    private final ResponseService responseService;

    @Value("${message.apiKey}")
    private String apiKey;

    @Value("${message.secret}")
    private String apiSecret;

    // 메세지 전송
    @GetMapping("/send-message")
    public SingleResult<String> sendMessage(@RequestParam String phoneNumber) {
        Optional<MessageLog> top1ByAuthorization = messageLogService.searchTop1ByPhoneNumber(phoneNumber);

        //유호성 값 체크
        if (!top1ByAuthorization.isEmpty()) {
            messageLogService.validationCheck(phoneNumber, top1ByAuthorization.get());
        }

        //회원 인증
        if (!authorizationService.existsByPhoneNumber(phoneNumber)) {
            Authorization authorization = Authorization.builder()
                    .phoneNumber(phoneNumber)
                    .phoneCheck(StatusValue.N)
                    .build();

            authorizationService.addAuthorization(authorization);
        }

        //메세지 로그
        MessageLog messageLog = MessageLog.builder()
                .phoneNumber(phoneNumber)
                .random(Util.createRandomNum())
                .regDate(LocalDateTime.now())
                .build();
        messageLogService.addMessageLog(messageLog);

        //메세지 전송
        String statusMessage = new MessageService(apiKey, apiSecret).sendMessage(messageLog).getStatusMessage();
        return responseService
                .getSingleResult(statusMessage);
    }

    // 메세지 확인
    @PutMapping("/check-message")
    public SingleResult<Boolean> checkMessage(@RequestParam String phoneNum, @RequestParam String randomNum) {
        MessageLog messageLog = messageLogService.searchTop1ByPhoneNumber(phoneNum)
                .orElseThrow(CMessageCheckFailedException::new);

        authorizationService.findByPhoneNumber(phoneNum);

        return responseService.getSingleResult(authorizationService.checkRandomNumber(phoneNum, messageLog.getRandom(), randomNum));
    }

    // 업데이트 메시지
    @PutMapping("/update-message")
    public SingleResult<String> update(@RequestParam String phone) {
        authorizationService.initAuthorization(phone);

        authorizationService.findByPhoneNumber(phone);

        return sendMessage(phone);
    }
}
