package com.conectacampo.service.impl;

import com.conectacampo.dto.request.HarvestRequest;
import com.conectacampo.dto.response.HarvestResponse;
import com.conectacampo.exception.ResourceNotFoundException;
import com.conectacampo.model.Farm;
import com.conectacampo.model.Harvest;
import com.conectacampo.model.Product;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.repository.FarmRepository;
import com.conectacampo.repository.HarvestRepository;
import com.conectacampo.repository.ProductRepository;
import com.conectacampo.service.HarvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final FarmRepository farmRepository;
    private final ProductRepository productRepository;

    @Override
    public List<HarvestResponse> getAllHarvests() {
        return harvestRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HarvestResponse getHarvestById(Long id) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cosecha no encontrada con id: " + id));
        return convertToResponse(harvest);
    }

    @Override
    public HarvestResponse createHarvest(HarvestRequest request) {
        validateHarvest(request);

        Farm farm = farmRepository.findById(request.getFarmId())
                .orElseThrow(() -> new ResourceNotFoundException("Finca no encontrada con id: " + request.getFarmId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + request.getProductId()));

        Harvest harvest = new Harvest();
        harvest.setFarm(farm);
        harvest.setProduct(product);
        harvest.setQuantity(request.getQuantity());
        harvest.setPriceSuggested(request.getPriceSuggested());
        harvest.setHarvestDate(request.getHarvestDate());
        harvest.setStatus(HarvestStatus.ACTIVE);

        Harvest saved = harvestRepository.save(harvest);
        return convertToResponse(saved);
    }

    @Override
    public HarvestResponse updateHarvest(Long id, HarvestRequest request) {
        Harvest harvest = harvestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cosecha no encontrada con id: " + id));

        if (request.getFarmId() != null) {
            Farm farm = farmRepository.findById(request.getFarmId())
                    .orElseThrow(() -> new ResourceNotFoundException("Finca no encontrada"));
            harvest.setFarm(farm);
        }
        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            harvest.setProduct(product);
        }
        if (request.getQuantity() != null) {
            validateQuantity(request.getQuantity());
            harvest.setQuantity(request.getQuantity());
        }
        if (request.getPriceSuggested() != null) {
            validatePrice(request.getPriceSuggested());
            harvest.setPriceSuggested(request.getPriceSuggested());
        }
        if (request.getHarvestDate() != null) {
            harvest.setHarvestDate(request.getHarvestDate());
        }

        Harvest updated = harvestRepository.save(harvest);
        return convertToResponse(updated);
    }

    @Override
    public void deleteHarvest(Long id) {
        if (!harvestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cosecha no encontrada con id: " + id);
        }
        harvestRepository.deleteById(id);
    }

    private void validateHarvest(HarvestRequest request) {
        validateQuantity(request.getQuantity());
        validatePrice(request.getPriceSuggested());
        validateDate(request.getHarvestDate());
    }

    private void validateQuantity(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
    }

    private void validateDate(LocalDate date) {
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cosecha no puede ser futura");
        }
    }

    private HarvestResponse convertToResponse(Harvest harvest) {
        HarvestResponse response = new HarvestResponse();
        response.setId(harvest.getId());
        response.setFarmName(harvest.getFarm().getName());
        response.setFarmerName(harvest.getFarm().getUser().getName());
        response.setProductName(harvest.getProduct().getName());
        response.setProductVariety(harvest.getProduct().getVariety());
        response.setQuantity(harvest.getQuantity());
        response.setPriceSuggested(harvest.getPriceSuggested());
        response.setPriceFairRef(harvest.getPriceFairRef());
        response.setHarvestDate(harvest.getHarvestDate());
        response.setStatus(harvest.getStatus().toString());
        response.setCreatedAt(harvest.getCreatedAt());
        return response;
    }
}