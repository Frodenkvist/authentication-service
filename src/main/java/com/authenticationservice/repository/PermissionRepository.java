package com.authenticationservice.repository;

import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Long deletePermissionByPersonAndName(String personnummer, PermissionName permissionName);
}
