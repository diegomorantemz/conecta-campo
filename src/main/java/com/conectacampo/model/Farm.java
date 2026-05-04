package com.conectacampo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "farms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String district;

    private Double locationLat;

    private Double locationLng;

    @Column(columnDefinition = "TEXT")
    private String description;
}