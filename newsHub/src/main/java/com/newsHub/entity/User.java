package com.newsHub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "dis_play_name")
    private String disPlayName;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
