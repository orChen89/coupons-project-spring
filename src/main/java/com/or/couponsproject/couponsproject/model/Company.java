package com.or.couponsproject.couponsproject.model;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = "password")
@Entity
@Table(name = "companies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private Integer password;

    @Transient
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Coupon> coupons;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
