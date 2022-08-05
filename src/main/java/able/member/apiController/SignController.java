package able.member.apiController;

import able.member.dto.UserLoginResponseDto;
import able.member.entity.User;
import able.member.security.JwtProvider;
import able.member.service.MessageService;
import able.member.service.SignService;
import able.member.utils.ResponseMessage;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Pattern;

import static able.member.utils.DefaultRes.createDefaultRes;

@Api(tags = "SignUp / LogIn")
@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam String email, @RequestParam String password) {

        User loginUser = signService.login(email, password);
        String token = jwtProvider.createToken(String.valueOf(loginUser.getUserNo()), loginUser.getRoles());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(loginUser, token);

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK, "SUCCESS", userLoginResponseDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public User saveUser(@RequestBody @Valid CreateUserRequest request) {
        


        //TODO 함수로 빼자.
        boolean matches = Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", request.getPassword());
        System.out.println(matches);
        if (!matches) {
            throw new IllegalArgumentException("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        }

        User user = User.builder()
                .mail(request.mail)
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.nickName)
                .name(request.name)
                .roles(Collections.singletonList("ROLE_USER"))
                .phoneNumber(request.phoneNumber).build();

        return signService.join(user);
    }

    @Data
    static class CreateUserRequest {
        private String mail;
        private String password;
        private String nickName;
        private String name;
        private String phoneNumber;
    }
}
