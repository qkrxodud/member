package able.member.apiController;

import able.member.dto.UserDtoResponse;
import able.member.entity.User;
import able.member.model.response.SingleResult;
import able.member.service.ResponseService;
import able.member.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(tags = "User")
@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

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
    public SingleResult<UserDtoResponse> findUserByMail(@PathVariable String mail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();

        User userByMail = userService.findUserByUserNoAndMail(principal.getUserNo(), mail);
        return responseService.getSingleResult(new UserDtoResponse(userByMail));
    }

}
