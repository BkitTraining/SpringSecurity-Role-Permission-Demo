package com.example.spring.security.demo.demo.service;

import com.example.spring.security.demo.demo.model.entity.AccountEntity;

import java.util.Optional;

public interface AccountService {

  Optional<AccountEntity> getByEmail(String email);

}
