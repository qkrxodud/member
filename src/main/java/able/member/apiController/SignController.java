package able.member.apiController;

import able.member.dto.AuthorStatusResponseDto;
import able.member.dto.UserLoginResponseDto;
import able.member.dto.UserSignResponseDto;
import able.member.entity.Authorization;
import able.member.entity.User;
import able.member.model.response.SingleResult;
import able.member.security.JwtProvider;
import able.member.service.AuthorizationService;
import able.member.service.ResponseService;
import able.member.service.SignService;
import able.member.service.UserService;
import able.member.utils.Util;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;


@Api(tags = "SignUp / LogIn")
@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;
    private final ResponseService responseService;
    private final UserService userService;

    // 이메일 로그인
    @GetMapping("/login-email")
    public SingleResult<UserLoginResponseDto> loginEmail(@RequestParam String email, @RequestParam String password) {
        User loginUser = signService.loginEmail(email, password);
        String token = jwtProvider.createToken(String.valueOf(loginUser.getUserNo()), loginUser.getRoles());

        return responseService.getSingleResult(new UserLoginResponseDto(loginUser, token));
    }

    // 핸드폰 로그인
    @GetMapping("/login-phone")
    public SingleResult<UserLoginResponseDto> loginPhone(@RequestParam String phone, @RequestParam String password) {
        User loginUser = signService.loginPhone(phone, password);
        String token = jwtProvider.createToken(String.valueOf(loginUser.getUserNo()), loginUser.getRoles());

        return responseService.getSingleResult(new UserLoginResponseDto(loginUser, token));
    }

    // 회원가입
    @PostMapping("/signup")
    public SingleResult<UserSignResponseDto> saveUser(@RequestBody @Valid CreateUserRequest request) {
        Authorization authorization = authorizationService.findByMailAndPhoneNumber(request.getMail(), request.getPhoneNumber());
        // 회원이 있는지 확인한다.
        userService.validateDuplicateMember(request.mail, request.phoneNumber);


        // 권환 확인
        authorization.checkStatusValue();

        // 패스워드 유효성체크
        Util.checkPasswordValidation(request.getPassword());

        User user = User.builder()
                .mail(request.mail)
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.nickName)
                .name(request.name)
                .roles(Collections.singletonList("ROLE_USER"))
                .phoneNumber(request.phoneNumber).build();

        User joinUser = signService.join(user);

        return responseService.getSingleResult(new UserSignResponseDto(joinUser));
    }


    // 비밀번호 업데이트
    @PutMapping("/user")
    public SingleResult<AuthorStatusResponseDto> update(@RequestParam String mail, @RequestParam String phone, @RequestParam String password) {
        Authorization authorization = authorizationService.findByMailAndPhoneNumber(mail, phone);

        // 권환 확인
        authorization.checkStatusValue();

        // 패스워드 유효성체크
        Util.checkPasswordValidation(password);

        signService.update(mail, phone, password);
        return responseService.getSingleResult(new AuthorStatusResponseDto(authorization));
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
