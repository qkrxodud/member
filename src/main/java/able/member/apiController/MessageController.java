package able.member.apiController;

import able.member.entity.Authorization;
import able.member.entity.MessageLog;
import able.member.entity.StatusValue;
import able.member.service.AuthorizationService;
import able.member.service.MessageLogService;
import able.member.service.MessageService;
import able.member.utils.ResponseMessage;
import able.member.utils.Util;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

import static able.member.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageLogService messageLogService;
    private final AuthorizationService authorizationService;

    @Value("${message.apiKey}")
    private String apiKey;

    @Value("${message.secret}")
    private String apiSecret;

    // 메세지 전송
    @GetMapping("/send-message")
    public ResponseEntity sendMessage(@RequestParam String phoneNumber) {
        Optional<MessageLog> top1ByAuthorization = messageLogService.searchTop1ByPhoneNumber(phoneNumber);
        if (!top1ByAuthorization.isEmpty()) {
            // 하루 전송량 10회
            messageLogService.checkSmsCount(phoneNumber);
            // 3분 이내에 시간 체크
            messageLogService.checkSmsDateTime(top1ByAuthorization.get());
        }
        //난수 생성
        String randomNum = Util.createRandomNum();

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
                .random(randomNum)
                .regDate(LocalDateTime.now())
                .build();
        messageLogService.addMessageLog(messageLog);

        //메세지 전송
        MessageService messageService = new MessageService(apiKey, apiSecret);
        SingleMessageSentResponse singleMessageSentResponse = messageService.sendMessage(messageLog);

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", singleMessageSentResponse.getStatusMessage()), HttpStatus.OK);
    }

    // 메세지 확인
    @GetMapping("/check-message")
    public ResponseEntity checkMessage(@RequestParam String phoneNum, @RequestParam String randomNum) {
        MessageLog messageLog = messageLogService.searchTop1ByPhoneNumber(phoneNum)
                .orElseThrow(() -> new IllegalStateException("메세지가 발송되지 않았습니다. 잠시기다려주세요."));
        System.out.println(messageLog.getRandom());

        Boolean result = authorizationService.checkRandomNumber(phoneNum, messageLog.getRandom(), randomNum);

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", result), HttpStatus.OK);
    }
}
