package com.or.couponsproject.couponsproject.model;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;
import javax.persistence.*;


@Data
@ToString
@Entity
@Builder
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private Integer password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
