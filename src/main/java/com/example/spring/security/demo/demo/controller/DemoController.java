package com.example.spring.security.demo.demo.controller;

import com.example.spring.security.demo.demo.exception.ApplicationException;
import com.example.spring.security.demo.demo.model.payload.LoginRequest;
import com.example.spring.security.demo.demo.model.payload.LoginResponse;
import com.example.spring.security.demo.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemoController {

  private final UserService userService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) throws ApplicationException {
    return LoginResponse.builder()
        .token(userService.login(loginRequest))
        .build();
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @SecurityRequirement(name = "API-KEY")
  public String callAdmin() {
    return "this is admin";
  }

  @GetMapping("/user")
  @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
  @SecurityRequirement(name = "API-KEY")
  public String callUser() {
    return "this is user";
  }


  @GetMapping("/staff")
  @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE')")
  @SecurityRequirement(name = "API-KEY")
  public String callStaff() {
    return "this is staff";
  }

}
