package com.example.spring.security.demo.demo.model.repository;

import com.example.spring.security.demo.demo.model.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

  Optional<PermissionEntity> getByName(String name);

  List<PermissionEntity> getByNameIn(List<String> name);

}
