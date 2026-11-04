package com.newsHub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "pass_word",nullable = false)
    private String passWord;
    @Column(name = "display_name")
    private String disPlayName;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToMany
    @JoinTable(name = "users_roles",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}
