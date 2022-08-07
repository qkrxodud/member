package abley.member.dto;

import abley.member.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
public class UserSignResponseDto {
    private final Long userNo;
    private final List<String> roles;
    private final LocalDateTime createDateTime;
    private final Collection<? extends GrantedAuthority> authorities;


    public UserSignResponseDto(User user) {
        this.userNo = user.getUserNo();
        this.roles = user.getRoles();
        this.createDateTime = LocalDateTime.now();
        this.authorities = user.getAuthorities();
    }
}
