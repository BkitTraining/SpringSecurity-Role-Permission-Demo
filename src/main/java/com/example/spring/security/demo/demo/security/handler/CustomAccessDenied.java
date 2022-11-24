package com.example.spring.security.demo.demo.security.handler;

import com.example.spring.security.demo.demo.controller.handler.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class CustomAccessDenied implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException e) throws IOException {
    log.error("Access denied - {}", e.getMessage(), e);
    response.setContentType("application/json");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    ErrorDetail errorDetail = ErrorDetail.builder()
        .errorMessage(e.getMessage()).build();
    response.getOutputStream().println(objectMapper.writeValueAsString(errorDetail));
  }

}
