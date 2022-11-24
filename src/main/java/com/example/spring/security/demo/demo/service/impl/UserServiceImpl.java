package com.example.spring.security.demo.demo.service.impl;

import com.example.spring.security.demo.demo.exception.ApplicationException;
import com.example.spring.security.demo.demo.model.dto.AccountInfo;
import com.example.spring.security.demo.demo.model.payload.LoginRequest;
import com.example.spring.security.demo.demo.model.repository.AccountRepository;
import com.example.spring.security.demo.demo.service.UserService;
import com.example.spring.security.demo.demo.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final AccountRepository accountRepository;
  private final ModelMapper modelMapper;
  private final ObjectMapper objectMapper;
  private final JwtUtils jwtUtils;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public String login(LoginRequest loginRequest) throws ApplicationException {
    if (!StringUtils.hasText(loginRequest.getEmail()) || !StringUtils.hasText(loginRequest.getPassword())) {
      throw new ApplicationException("Invalid email or Password");
    }
    var accountEntity = accountRepository.getByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new ApplicationException("Account not found"));
    if (!passwordEncoder.matches(loginRequest.getPassword(), accountEntity.getPassword())) {
      throw new ApplicationException("Invalid email or Password");
    }
    AccountInfo accountInfo = modelMapper.map(accountEntity, AccountInfo.class);
    try {
      return jwtUtils.createAccountToken(objectMapper.writeValueAsString(accountInfo));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
