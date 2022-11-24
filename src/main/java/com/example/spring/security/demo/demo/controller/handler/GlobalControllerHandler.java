package com.example.spring.security.demo.demo.controller.handler;

import com.example.spring.security.demo.demo.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalControllerHandler {

  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorDetail problem(final ApplicationException e) {
    log.error("Error at {}", e.getMessage(), e);
    return e.getErrorDetail();
  }

}
