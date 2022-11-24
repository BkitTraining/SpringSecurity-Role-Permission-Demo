package com.example.spring.security.demo.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfo {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private List<RoleInfo> roles;

}
