package able.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    @Column(name = "mail", nullable = false, unique = true, length = 30)
    private String mail;
    @Column(name = "nick_name", nullable = false, length = 30)
    private String nickName;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;


    public static User CreateUser(String eMail, String nickName, String name, String phoneNumber) {
        User user = User.builder()
                .eMail(eMail)
                .nickName(nickName)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
        return user;
    }

    @Builder
    public User(String eMail, String nickName, String name, String phoneNumber) {
        Assert.hasText(eMail, "eMail must not be empty");
        Assert.hasText(nickName, "nickName must not be empty");
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(phoneNumber, "phoneNumber must not be empty");

        this.mail = eMail;
        this.nickName = nickName;
        this.name = name;
        this.phoneNumber = phoneNumber;

    }

}
