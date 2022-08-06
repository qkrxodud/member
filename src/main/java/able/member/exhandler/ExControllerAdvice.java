package able.member.exhandler;

import able.member.exhandler.exception.*;
import able.member.model.response.CommonResult;
import able.member.service.ResponseService;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExControllerAdvice {

    private  final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.info(String.valueOf(e));
        ErrorResult errorResult = new ErrorResult("userException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CAuthorizationNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationNotFoundException() {
        return responseService.getFailResult(Integer.parseInt("-1000"), "등록된 회원이 없습니다. 핸드폰 인증을 진행해주세요.");
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException() {
        return responseService.getFailResult(Integer.parseInt("-1001"), "권한인증을 찾을수 없습니다..");
    }

    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult emailLoginFailedException() {
        return responseService.getFailResult(Integer.parseInt("-1002"), "이메일로 로그인하는데 실패하였습니다.");
    }

    @ExceptionHandler(CPhoneLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult phoneLoginFailedException() {
        return responseService.getFailResult(Integer.parseInt("-1003"), "핸드폰으로 로그인하는데 실패하였습니다.");
    }

    @ExceptionHandler(CMessageSendFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendFailedException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1003"), "메세지 보내는데 실패하였습니다.");
    }

    @ExceptionHandler(CMessageSendTimeOverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendTimeOverException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1004"), "메세지는 2분후 재전송 가능합니다.");
    }

    @ExceptionHandler(CMessageSendCountOverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendCountOverException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1004"), "하루 메세지는 10개 미만으로 전송 가능합니다.");
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult userNotFoundException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1004"), "회원을 찾는데 실패하였습니다.");
    }

    @ExceptionHandler(CMessageCheckFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageCheckFailedException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1005"), "회원인증을 하는데 실패하였습니다..");
    }

    @Data
    @AllArgsConstructor
    public class ErrorResult {
        private String code;
        private String message;
    }
}
