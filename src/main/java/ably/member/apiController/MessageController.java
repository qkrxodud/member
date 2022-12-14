package ably.member.apiController;

import ably.member.dto.MessageResponseDto;
import ably.member.entity.Authorization;
import ably.member.entity.MessageLog;
import ably.member.entity.StatusValue;
import ably.member.exhandler.exception.CMessageCheckFailedException;
import ably.member.model.response.SingleResult;
import ably.member.service.AuthorizationService;
import ably.member.service.MessageLogService;
import ably.member.service.MessageService;
import ably.member.service.ResponseService;
import ably.member.utils.Util;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = "Message")
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
    public SingleResult<MessageResponseDto> sendMessage(@RequestParam String mail, @RequestParam String phoneNumber) {
        Optional<MessageLog> top1ByAuthorization = messageLogService.searchTop1ByPhoneNumber(phoneNumber);

        //유호성 값 체크
        if (!top1ByAuthorization.isEmpty()) {
            messageLogService.validationCheck(phoneNumber, top1ByAuthorization.get());
        }

        //회원 인증
        if (!authorizationService.existsByPhoneNumber(phoneNumber)) {
            Authorization authorization = Authorization.builder()
                    .mail(mail)
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
                .getSingleResult(new MessageResponseDto(phoneNumber, statusMessage));
    }

    // 메세지 확인
    @PutMapping("/check-message")
    public SingleResult<MessageResponseDto> checkMessage(@RequestParam String mail, @RequestParam String phoneNum, @RequestParam String randomNum) {
        MessageLog messageLog = messageLogService.searchTop1ByPhoneNumber(phoneNum)
                .orElseThrow(CMessageCheckFailedException::new);

        authorizationService.findByMailAndPhoneNumber(mail, phoneNum); // SMS 인증이 되지 않았습니다.
        Boolean checkAuth = authorizationService.checkRandomNumber(mail, phoneNum, messageLog.getRandom(), randomNum);

        return responseService.getSingleResult(new MessageResponseDto(phoneNum, checkAuth.toString()));
    }

    // 업데이트 메시지
    @PutMapping("/send-update-message")
    public SingleResult<MessageResponseDto> sendUpdateMessage(@RequestParam String mail, @RequestParam String phone) {
        authorizationService.initAuthorization(mail, phone);
        authorizationService.findByMailAndPhoneNumber(mail, phone);  // SMS 인증이 되지 않았습니다.

        return sendMessage(mail, phone);
    }

    // 업데이트 메시지 확인
    @PutMapping("/check-update-message")
    public SingleResult<MessageResponseDto> checkUpdateMessage(@RequestParam String mail, @RequestParam String phoneNum, @RequestParam String randomNum) {
        MessageLog messageLog = messageLogService.searchTop1ByPhoneNumber(phoneNum)
                .orElseThrow(CMessageCheckFailedException::new);

        authorizationService.findByMailAndPhoneNumber(mail, phoneNum); // SMS 인증이 되지 않았습니다.
        Boolean checkAuth = authorizationService.checkRandomNumber(mail, phoneNum, messageLog.getRandom(), randomNum);


        return responseService.getSingleResult(new MessageResponseDto(phoneNum, checkAuth.toString()));
    }
}
