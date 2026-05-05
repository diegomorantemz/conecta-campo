package com.conectacampo.controller;

import com.conectacampo.dto.request.HarvestRequest;
import com.conectacampo.dto.response.HarvestResponse;
import com.conectacampo.service.HarvestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HarvestRestController {

    private final HarvestService harvestService;

    //Obtener todas las cosechas
    @GetMapping
    public ResponseEntity<List<HarvestResponse>> getAllHarvests() {
        return ResponseEntity.ok(harvestService.getAllHarvests());
    }

    //Obtener una cosecha por ID
    @GetMapping("/{id}")
    public ResponseEntity<HarvestResponse> getHarvestById(@PathVariable Long id) {
        return ResponseEntity.ok(harvestService.getHarvestById(id));
    }

    //Crear una nueva cosecha
    @PostMapping
    public ResponseEntity<HarvestResponse> createHarvest(@Valid @RequestBody HarvestRequest request) {
        HarvestResponse response = harvestService.createHarvest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Actualizar una cosecha existente
    @PutMapping("/{id}")
    public ResponseEntity<HarvestResponse> updateHarvest(@PathVariable Long id,
                                                         @Valid @RequestBody HarvestRequest request) {
        HarvestResponse response = harvestService.updateHarvest(id, request);
        return ResponseEntity.ok(response);
    }

    //Eliminar una cosecha
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        harvestService.deleteHarvest(id);
        return ResponseEntity.noContent().build();
    }
}