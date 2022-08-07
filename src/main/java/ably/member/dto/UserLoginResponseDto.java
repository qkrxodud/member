package ably.member.dto;

import ably.member.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
public class UserLoginResponseDto {
    private final Long userNo;
    private final List<String> roles;
    private final LocalDateTime createDateTime;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String token;


    public UserLoginResponseDto(User user, String token) {
        this.userNo = user.getUserNo();
        this.roles = user.getRoles();
        this.createDateTime = LocalDateTime.now();
        this.authorities = user.getAuthorities();
        this.token = token;
    }
}
