package com.soyaa.boardserver.exception;


import com.soyaa.boardserver.dto.response.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"RuntimeException",e.getMessage(),e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(),commonResponse.getStatus());
    }

    @ExceptionHandler(BoardServerException.class)
    public ResponseEntity<Object> handleBoardServerException(RuntimeException e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"BoardServerException",e.getMessage(),e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(),commonResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(RuntimeException e) {
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"BoardServerException",e.getMessage(),e.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(),commonResponse.getStatus());
    }
}
