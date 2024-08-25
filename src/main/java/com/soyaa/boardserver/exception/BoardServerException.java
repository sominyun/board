package com.soyaa.boardserver.exception;

import org.springframework.http.HttpStatus;

public class BoardServerException extends RuntimeException{
    HttpStatus code;
    String msg;
}
