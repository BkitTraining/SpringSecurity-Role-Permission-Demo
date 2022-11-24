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
public class RoleInfo {

  private Long id;
  private String name;
  private List<PermissionInfo> permissions;

}
