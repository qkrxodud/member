package abley.member.exhandler;

import abley.member.exhandler.exception.*;
import abley.member.model.response.CommonResult;
import abley.member.service.ResponseService;
import jdk.jshell.spi.ExecutionControl.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExControllerAdvice {

    private  final ResponseService responseService;

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.info(String.valueOf(e));
        ErrorResult errorResult = new ErrorResult("userException", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    /**
     * SMS 오류 -1000
     */
    @ExceptionHandler(CMessageSendFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendFailedException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1001"), "SMS 보내는데 실패하였습니다.");
    }

    @ExceptionHandler(CMessageSendTimeOverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendTimeOverException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1002"), "SMS 2초후 재전송 가능합니다.");
    }
    @ExceptionHandler(CMessageSendCountOverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageSendCountOverException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1003"), "하루 SMS 10개 미만으로 전송 가능합니다.");
    }

    @ExceptionHandler(CMessageCheckFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult messageCheckFailedException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-1004"), "회원인증을 하는데 실패하였습니다.");
    }

    /**
     * 권한 오류 -2000
     */
    @ExceptionHandler(CAuthorizationNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationNotFoundException() {
        return responseService.getFailResult(Integer.parseInt("-2000"), "SMS 인증이 되지 않았습니다.");
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException() {
        return responseService.getFailResult(Integer.parseInt("-2001"), "권한인증을 찾을수 없습니다..");
    }


    /**
     * 유저 오류 -3000
     */
    @ExceptionHandler(CEmailLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult emailLoginFailedException() {
        return responseService.getFailResult(Integer.parseInt("-3001"), "이메일로 로그인하는데 실패하였습니다.");
    }

    @ExceptionHandler(CPhoneLoginFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult phoneLoginFailedException() {
        return responseService.getFailResult(Integer.parseInt("-3002"), "핸드폰으로 로그인하는데 실패하였습니다.");
    }


    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult userNotFoundException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-3003"), "회원을 찾는데 실패하였습니다.");
    }

    @ExceptionHandler(CUserDuplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult userDuplicationException(Exception e) {
        return responseService.getFailResult(Integer.parseInt("-3004"), "이미 회원이 존재합니다.");
    }

    @Data
    @AllArgsConstructor
    public class ErrorResult {
        private String code;
        private String message;
    }
}
