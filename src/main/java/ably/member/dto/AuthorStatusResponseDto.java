package ably.member.dto;

import ably.member.entity.Authorization;
import ably.member.entity.StatusValue;
import lombok.Getter;

@Getter
public class AuthorStatusResponseDto {
    private final String mail;
    private final String phoneNum;
    private final StatusValue statusValue;


    public AuthorStatusResponseDto(Authorization authorization) {
        this.mail = authorization.getMail();
        this.phoneNum = authorization.getPhoneNumber();
        this.statusValue = authorization.getPhoneCheck();
    }
}
