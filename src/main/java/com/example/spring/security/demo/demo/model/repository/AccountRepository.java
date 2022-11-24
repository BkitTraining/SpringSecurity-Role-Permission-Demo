package com.example.spring.security.demo.demo.model.repository;

import com.example.spring.security.demo.demo.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

  Optional<AccountEntity> getByEmail(String email);

}
