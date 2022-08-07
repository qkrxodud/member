package ably.member.service;

import ably.member.entity.MessageLog;
import lombok.Setter;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Setter
public class MessageService {

    DefaultMessageService messageService;


    public  MessageService(String apiKey, String apiSecret) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    // 메시지 전송
    public SingleMessageSentResponse sendMessage(MessageLog messageLog) {
        SingleMessageSentResponse response = null;
        Message message = new Message();
        message.setFrom("01034047799");
        message.setTo(messageLog.getPhoneNumber());
        message.setText("본인확인\n인증번호("+messageLog.getRandom()+")입력\n시 정상처리 됩니다.");
        try {
            //TODO 풀어줘야 작동한다. 현재는 테스트
            response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
