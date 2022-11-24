package com.example.spring.security.demo.demo.security.service;

import com.example.spring.security.demo.demo.model.dto.AccountInfo;
import com.example.spring.security.demo.demo.model.dto.PermissionInfo;
import com.example.spring.security.demo.demo.model.dto.RoleInfo;
import com.example.spring.security.demo.demo.model.entity.AccountEntity;
import com.example.spring.security.demo.demo.model.entity.PermissionEntity;
import com.example.spring.security.demo.demo.model.entity.RoleEntity;
import com.example.spring.security.demo.demo.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

  private final AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    AccountEntity accountEntity;
    try {
      accountEntity = accountService.getByEmail(email).orElseThrow();
    } catch (Exception e) {
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    // get Permission from ROLE
    List<String> privileges = new ArrayList<>(); // Spring will use this privilege values to validate authorization
    List<RoleEntity> roles = accountEntity.getRoles();
    List<PermissionEntity> permissions = new ArrayList<>();
    roles.forEach(i -> {
      privileges.add(i.getName());
      permissions.addAll(i.getPermissions());
    });
    permissions.forEach(i -> privileges.add(i.getName()));

    List<GrantedAuthority> springAuthorities = new ArrayList<>();
    for (String privilege : privileges) {
      springAuthorities.add(new SimpleGrantedAuthority(privilege));
    }
    return new User(accountEntity.getEmail(), accountEntity.getPassword(), springAuthorities);
  }

}
