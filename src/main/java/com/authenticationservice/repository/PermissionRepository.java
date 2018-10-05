package com.authenticationservice.repository;

import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM permission WHERE personnummer = :personnummer AND name = :permissionName", nativeQuery = true)
    int deletePermissionByPersonnummerAndName(@Param("personnummer") String personnummer, @Param("permissionName") PermissionName permissionName);
}
