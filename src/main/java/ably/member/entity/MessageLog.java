package ably.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "phone_number", nullable = false, length = 30)
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phoneNumber;

    @Column(name = "random", nullable = false, length = 6)
    @NotBlank(message = "랜덤번호는 필수 입력 값입니다.")
    private String random;

    @Column(name = "regDate", nullable = false)
    private LocalDateTime regDate;

}
