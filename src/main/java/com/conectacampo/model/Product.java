package com.conectacampo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String name;

    @Size(max = 50, message = "La variedad no puede exceder los 50 caracteres")
    @Column(length = 50)
    private String variety;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(length = 50)
    private String category;

    @NotBlank(message = "La unidad es obligatoria")
    @Column(length = 20)
    private String unit;

    @NotNull(message = "El precio mínimo es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio mínimo debe ser mayor a 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal minPrice;

    @NotNull(message = "El precio máximo es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio máximo debe ser mayor a 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal maxPrice;

    @Size(max = 2, message = "El mes de inicio debe tener 2 dígitos")
    private String seasonStart;

    @Size(max = 2, message = "El mes de fin debe tener 2 dígitos")
    private String seasonEnd;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String description;
}