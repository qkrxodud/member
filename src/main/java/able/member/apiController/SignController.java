package able.member.apiController;

import able.member.dto.UserLoginResponseDto;
import able.member.entity.Authorization;
import able.member.entity.StatusValue;
import able.member.entity.User;
import able.member.exhandler.exception.CUserNotFoundException;
import able.member.model.response.SingleResult;
import able.member.security.JwtProvider;
import able.member.service.AuthorizationService;
import able.member.service.ResponseService;
import able.member.service.SignService;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.regex.Pattern;


@Api(tags = "SignUp / LogIn")
@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;
    private final ResponseService responseService;

    @GetMapping("/login-email")
    public SingleResult<UserLoginResponseDto> loginEmail(@RequestParam String email, @RequestParam String password) {
        User loginUser = signService.loginEmail(email, password);
        String token = jwtProvider.createToken(String.valueOf(loginUser.getUserNo()), loginUser.getRoles());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(loginUser, token);

        return responseService.getSingleResult(userLoginResponseDto);
    }

    @GetMapping("/login-phone")
    public SingleResult<UserLoginResponseDto> loginPhone(@RequestParam String phone, @RequestParam String password) {
        User loginUser = signService.loginPhone(phone, password);
        String token = jwtProvider.createToken(String.valueOf(loginUser.getUserNo()), loginUser.getRoles());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(loginUser, token);

        return responseService.getSingleResult(userLoginResponseDto);
    }

    @PostMapping("/signup")
    public SingleResult<User> saveUser(@RequestBody @Valid CreateUserRequest request) {
        // TODO 주석 열어줘야된다.
        Authorization authorization = authorizationService.findByPhoneNumber(request.getPhoneNumber());

        if (authorization.getPhoneCheck() == StatusValue.N) {
            throw new IllegalArgumentException("핸드폰 인증 후 회원가입바랍니다.");
        }

        //TODO 함수로 빼자.
        boolean matches = Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", request.getPassword());
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

        return responseService.getSingleResult(signService.join(user));
    }

    @PutMapping("/user")
    public SingleResult<Authorization> update(@RequestParam String phone, @RequestParam String password) {
        Authorization authorization = authorizationService.findByPhoneNumber(phone);

        if (authorization.getPhoneCheck() == StatusValue.Y) {
            signService.update(phone, password);
        }

        return responseService.getSingleResult(authorization);
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
