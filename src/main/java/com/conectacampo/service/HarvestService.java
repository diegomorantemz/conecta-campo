package com.conectacampo.service;

import com.conectacampo.dto.request.HarvestRequest;
import com.conectacampo.dto.response.HarvestResponse;

import java.util.List;

public interface HarvestService {
    List<HarvestResponse> getAllHarvests();
    HarvestResponse getHarvestById(Long id);
    HarvestResponse createHarvest(HarvestRequest request);
    HarvestResponse updateHarvest(Long id, HarvestRequest request);
    void deleteHarvest(Long id);
}