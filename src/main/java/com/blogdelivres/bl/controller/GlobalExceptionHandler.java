package com.blogdelivres.bl.controller;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    ProblemDetail erroDetail = null;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException (Exception e){
        if(e instanceof BadCredentialsException){
            erroDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            erroDetail.setProperty("access_denied_reason", "Problem with your credentials, please log in");
        }
        if(e instanceof MalformedJwtException){
            erroDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            erroDetail.setProperty("access_denied_reason", "Problem with your JWT, please log in.");
        }
        if(e instanceof ExpiredJwtException){
            erroDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            erroDetail.setProperty("access_denied_reason", "Problem with your JWT, please log in.");
        }
        return erroDetail;
    }

}
