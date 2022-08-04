package able.member.service;

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

    public SingleMessageSentResponse sendMessage(String toNumber, String randomNumber) {
        SingleMessageSentResponse response = null;
        Message message = new Message();
        message.setFrom("01034047799");
        message.setTo(toNumber);
        message.setText("본인확인\n인증번호("+randomNumber+")입력\n시 정상처리 됩니다.");
        try {
            response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
