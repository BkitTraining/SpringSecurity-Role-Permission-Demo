package com.example.spring.security.demo.demo.exception;

import com.example.spring.security.demo.demo.controller.handler.ErrorDetail;
import lombok.Getter;

@Getter
public class ApplicationException extends Exception {

  protected final transient ErrorDetail errorDetail;

  public ApplicationException(String errorMessage) {
    super(errorMessage);

    this.errorDetail = ErrorDetail.builder()
        .errorMessage(errorMessage)
        .build();
  }

  public ApplicationException(String errorMessage, Throwable rootCause) {
    super(errorMessage, rootCause);

    this.errorDetail = ErrorDetail.builder()
        .errorMessage(errorMessage)
        .build();
  }
}