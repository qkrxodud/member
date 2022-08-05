package able.member.apiController;

import able.member.entity.ConfirmLog;
import able.member.service.MessageDataService;
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
    private final MessageDataService messageDataService;

    @Value("${message.apiKey}")
    private String apiKey;

    @Value("${message.secret}")
    private String apiSecret;

    @GetMapping("/send-message")
    public ResponseEntity sendMessage(@RequestParam String phoneNumber) {
        Optional<ConfirmLog> top1ByAuthorization = messageDataService.findTop1ByAuthorization(phoneNumber);
        if (!top1ByAuthorization.isEmpty()) {
            // 하루 전송량 10회
            messageDataService.checkSmsCount(phoneNumber);
            // 3분 이내에 시간 체크
            messageDataService.checkSmsDateTime(top1ByAuthorization.get());
        }

        //난수 생성
        String randomNum = Util.createRandomNum();

        ConfirmLog authorization = ConfirmLog.builder()
                .phoneNumber(phoneNumber)
                .random(randomNum)
                .regDate(LocalDateTime.now())
                .build();
        messageDataService.addAuthorization(authorization);

        MessageService messageService = new MessageService(apiKey, apiSecret);
        SingleMessageSentResponse singleMessageSentResponse = messageService.sendMessage(authorization);

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", singleMessageSentResponse.getStatusMessage()), HttpStatus.OK);
    }
}
