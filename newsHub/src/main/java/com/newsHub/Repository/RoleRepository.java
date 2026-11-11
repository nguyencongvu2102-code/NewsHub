package com.newsHub.Repository;

import com.newsHub.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
    boolean existsByPermissions_PermissionId(Integer permissionId);
    boolean existsByNameAndRoleIdNot(String name,Integer roleId);
    boolean existsByName(String name);

    @EntityGraph(attributePaths = "permissions")
    @Query("Select distinct r from Role r")
    List<Role> findAllWithPermissions();

    @EntityGraph(attributePaths = "permissions")
    @Query("Select r from Role r where Role = :roleId")
    Optional<Role> findByIdWithPermissions(@Param("roleId") Integer roleId);
}
