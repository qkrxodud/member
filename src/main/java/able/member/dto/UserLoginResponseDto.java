package able.member.dto;

import able.member.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLoginResponseDto {
    private final Long userNo;
    private final List<String> roles;
    private final LocalDateTime createDateTime;
    private final String token;


    public UserLoginResponseDto(User user, String token) {
        this.userNo = user.getUserNo();
        this.roles = user.getRoles();
        this.createDateTime = LocalDateTime.now();
        this.token = token;
    }
}
