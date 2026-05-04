package com.conectacampo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String variety;

    @Column(length = 50)
    private String category;

    @Column(length = 20)
    private String unit;

    @Column(precision = 10, scale = 2)
    private BigDecimal minPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxPrice;

    private String seasonStart;

    private String seasonEnd;

    @Column(columnDefinition = "TEXT")
    private String description;
}