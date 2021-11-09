package com.kyr.springjpa.handler;

import com.kyr.springjpa.exception.ApiException;
import com.kyr.springjpa.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> httpRequestMethodNotExceptionHandler(HttpRequestMethodNotSupportedException e){
        log.error("httpRequestExceptionHandler : {}" , e);

        final ErrorResponse errorResponse = ErrorResponse.create()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(e.getMessage());

        return new ResponseEntity<>(errorResponse , HttpStatus.METHOD_NOT_ALLOWED);
    }

    //@Valid 검증 실패 시 Catch
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> invalidParameterExceptionHandler(ApiException e) {
        log.error("invalidParameterExceptionHandler : {}", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = getErrorResponse(errorCode , e);

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    //CustomException을 상속받은 클래스가 예외를 발생 시킬 시, Catch하여 ErrorResponse를 반환한다.
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e) {
        log.error("customExceptionHandler : {}", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response = getErrorResponse(errorCode , e);

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    //모든 예외를 핸들링하여 ErrorResponse 형식으로 반환한다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        log.error("exceptionHandler", e);

        ErrorResponse response
                = ErrorResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse getErrorResponse(ErrorCode errorCode , Exception e) {
        return ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.toString());
    }
}
