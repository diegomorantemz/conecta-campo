package com.conectacampo.model;

import com.conectacampo.model.enums.HarvestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "harvests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Harvest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal priceSuggested;

    @Column(precision = 10, scale = 2)
    private BigDecimal priceFairRef;

    private LocalDate harvestDate;

    @Enumerated(EnumType.STRING)
    private HarvestStatus status = HarvestStatus.ACTIVE;
}