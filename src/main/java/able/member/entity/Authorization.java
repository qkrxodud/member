package able.member.entity;

import able.member.exhandler.exception.CAuthorizationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "mail", nullable = false, unique = true, length = 30)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String mail;

    @Column(name = "phone_number", nullable = false, length = 30, unique = true)
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_status",  nullable = false)
    StatusValue phoneCheck;

    public void changePhoneCheckValue(StatusValue statusValue) {
        this.phoneCheck = statusValue;
    }

    public void checkStatusValue() {
        if (this.phoneCheck == StatusValue.N) {
            throw new CAuthorizationNotFoundException();
        }
    }
}
