package com.conectacampo.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class HarvestRequest {
    private Long farmId;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal priceSuggested;
    private LocalDate harvestDate;
}