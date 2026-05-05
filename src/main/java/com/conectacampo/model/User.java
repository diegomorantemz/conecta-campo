package com.conectacampo.model;

import com.conectacampo.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, length = 8)
    private String dni;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 20)
    private String phone;

    @Column(length = 50)
    private String department;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String district;

    @Column(nullable = false)
    private Boolean enabled = true;
}