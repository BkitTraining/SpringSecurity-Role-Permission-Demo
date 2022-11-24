package com.example.spring.security.demo.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "roles")
  private List<AccountEntity> accounts;

  @ManyToMany
  @JoinTable(
      name = "roles_privileges",
      joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "permission_id", referencedColumnName = "id"))
  private List<PermissionEntity> permissions;

}
