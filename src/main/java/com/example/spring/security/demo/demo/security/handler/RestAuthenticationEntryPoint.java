package com.example.spring.security.demo.demo.security.handler;

import com.example.spring.security.demo.demo.controller.handler.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    log.error("Responding with unauthorized error. Message - {}", e.getMessage(), e);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    ErrorDetail errorDetail = ErrorDetail.builder()
        .errorMessage(e.getMessage())
        .build();
    response.setContentType("application/json");
    response.getOutputStream().println(objectMapper.writeValueAsString(errorDetail));

  }

}
