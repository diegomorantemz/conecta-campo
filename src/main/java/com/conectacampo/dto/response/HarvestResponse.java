package com.conectacampo.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HarvestResponse {
    private Long id;
    private String farmName;
    private String farmerName;
    private String productName;
    private String productVariety;
    private BigDecimal quantity;
    private BigDecimal priceSuggested;
    private BigDecimal priceFairRef;
    private LocalDate harvestDate;
    private String status;
    private LocalDateTime createdAt;
}