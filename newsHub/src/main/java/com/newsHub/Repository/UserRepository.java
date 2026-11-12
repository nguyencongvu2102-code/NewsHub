package com.newsHub.Repository;


import com.newsHub.entity.Role;
import com.newsHub.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    boolean existsByRolesContaining(Role role);

    @EntityGraph(attributePaths = "roles")
    @Query("select u from User u where u.isDeleted = false")
    List<User> findByIsDeletedFalse();


    @EntityGraph(attributePaths = "roles")
    @Query("select distinct u from User u where u.isDeleted = false")
    List<User> findByUsers();

    @EntityGraph(attributePaths = "roles")
    @Query("select u from User u where u.id = :userId and u.isDeleted = false")
    Optional<User> findByUserId(@Param("userId") Integer userId);


    @EntityGraph(attributePaths = "roles")
    @Query("Select distinct u from User u where u.isDeleted = true")
    List<User> findDeletedUsers();

}
