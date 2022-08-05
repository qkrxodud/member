package able.member.apiController;

import able.member.dto.UserLoginResponseDto;
import able.member.entity.User;
import able.member.service.UserService;
import able.member.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static able.member.utils.DefaultRes.createDefaultRes;


@Api(tags = "User")
@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserController {

    private final UserService userService;


    // 메일로 유저 조회
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @ApiOperation(value = "회원 검색 (메일)", notes = "이메일로 회원을 검색합니다.")
    @GetMapping("/api/findUser/{mail}")
    public ResponseEntity findUserByMail(@PathVariable String mail) {
        User userByMail = userService.findUserByMail(mail);

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(userByMail, "");

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", userLoginResponseDto), HttpStatus.OK);
    }

}
