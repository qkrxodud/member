package able.member.dto;

import able.member.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDtoResponse {
    private final String mail;
    private final String nickName;
    private final String name;
    private final String phoneNum;
    private final List<String> roles;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDtoResponse(User user) {
        this.mail = user.getMail();
        this.nickName = user.getNickName();
        this.name = user.getName();
        this.phoneNum = user.getPhoneNumber();
        this.roles = user.getRoles();
        this.authorities = user.getAuthorities();
    }
}
