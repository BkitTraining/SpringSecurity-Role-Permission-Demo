package com.example.spring.security.demo.demo.service;

import com.example.spring.security.demo.demo.exception.ApplicationException;
import com.example.spring.security.demo.demo.model.dto.AccountInfo;
import com.example.spring.security.demo.demo.model.payload.LoginRequest;

public interface UserService {

  String login(LoginRequest loginRequest) throws ApplicationException;

}
