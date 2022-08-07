package abley.member.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private final String phoneNum;
    private final String successMsg;

    public MessageResponseDto(String phoneNum, String successMsg) {
        this.phoneNum = phoneNum;
        this.successMsg = successMsg;
    }
}
