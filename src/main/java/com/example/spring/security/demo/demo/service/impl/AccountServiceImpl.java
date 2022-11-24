package com.example.spring.security.demo.demo.service.impl;

import com.example.spring.security.demo.demo.model.entity.AccountEntity;
import com.example.spring.security.demo.demo.model.repository.AccountRepository;
import com.example.spring.security.demo.demo.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  @Override
  public Optional<AccountEntity> getByEmail(String email) {
    return accountRepository.getByEmail(email);
  }


}
