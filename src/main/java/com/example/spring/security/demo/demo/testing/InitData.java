package com.example.spring.security.demo.demo.testing;

import com.example.spring.security.demo.demo.model.entity.AccountEntity;
import com.example.spring.security.demo.demo.model.entity.PermissionEntity;
import com.example.spring.security.demo.demo.model.entity.RoleEntity;
import com.example.spring.security.demo.demo.model.repository.AccountRepository;
import com.example.spring.security.demo.demo.model.repository.PermissionRepository;
import com.example.spring.security.demo.demo.model.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class InitData {

  private final AccountRepository accountRepository;

  private final RoleRepository roleRepository;

  private final PermissionRepository permissionRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  private final String[] PERMISSIONS = new String[]{
      "READ_PRIVILEGE",
      "CREATE_PRIVILEGE",
      "UPDATE_PRIVILEGE",
      "DELETE_PRIVILEGE"
  };

  private final String[] ROLES = new String[]{
      "ROLE_ADMIN",
      "ROLE_STAFF",
      "ROLE_USER"
  };

  @PostConstruct
  public void initData() {
    initPermission();
    initRole();
    initUser();
  }

  public void initPermission() {
    for (String permission : PERMISSIONS) {
      if (permissionRepository.getByName(permission).isEmpty()) {
        permissionRepository.save(PermissionEntity
            .builder()
            .name(permission)
            .build());
      }
    }

  }

  public void initRole() {
    for (String role : ROLES) {
      if (roleRepository.getByName(role).isEmpty()) {
        RoleEntity build = RoleEntity
            .builder()
            .name(role)
            .build();
        if (role.equals("ROLE_ADMIN")) {
          build.setPermissions(permissionRepository.findAll());
        }
        if (role.equals("ROLE_STAFF")) {
          build.setPermissions(permissionRepository.getByNameIn(List.of("READ_PRIVILEGE", "CREATE_PRIVILEGE", "UPDATE_PRIVILEGE")));
        }
        if (role.equals("ROLE_USER")) {
          build.setPermissions(List.of(permissionRepository.getByName("READ_PRIVILEGE").orElseThrow()));
        }
        roleRepository.save(build);
      }
    }
  }

  public void initUser() {


    AccountEntity admin = AccountEntity.builder()
        .firstName("ADMIN")
        .lastName("ADMIN")
        .email("admin@gmail.com")
        .roles(List.of(roleRepository.getByName("ROLE_ADMIN").orElseThrow()))
        .password(passwordEncoder.encode("123"))
        .build();
    if (accountRepository.getByEmail(admin.getEmail()).isEmpty()) {
      accountRepository.save(admin);
    }

    AccountEntity staff = AccountEntity.builder()
        .firstName("STAFF")
        .lastName("STAFF")
        .email("staff@gmail.com")
        .roles(List.of(roleRepository.getByName("ROLE_STAFF").orElseThrow()))
        .password(passwordEncoder.encode("123"))
        .build();
    if (accountRepository.getByEmail(staff.getEmail()).isEmpty()) {
      accountRepository.save(staff);
    }
    AccountEntity user = AccountEntity.builder()
        .firstName("USER")
        .lastName("USER")
        .email("user@gmail.com")
        .roles(List.of(roleRepository.getByName("ROLE_USER").orElseThrow()))
        .password(passwordEncoder.encode("123"))
        .build();
    if (accountRepository.getByEmail(user.getEmail()).isEmpty()) {
      accountRepository.save(user);
    }
  }

}
