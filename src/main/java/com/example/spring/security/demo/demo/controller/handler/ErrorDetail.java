package com.example.spring.security.demo.demo.controller.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ErrorDetail {

//  private Integer errorCode;
  private String errorMessage;

}

