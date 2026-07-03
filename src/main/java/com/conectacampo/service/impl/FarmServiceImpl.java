package com.conectacampo.service.impl;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import com.conectacampo.repository.FarmRepository;
import com.conectacampo.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;

    @Override
    public List<Farm> getFarmsByUser(User user) {
        return farmRepository.findByUser(user);
    }

    @Override
    public Farm saveFarm(Farm farm, User user) {
        validateFarm(farm);
        farm.setUser(user);
        return farmRepository.save(farm);
    }

    @Override
    public Farm updateFarm(Long id, Farm farm, User user) {
        Farm existing = farmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para editar esta finca");
        }

        validateFarm(farm);

        existing.setName(farm.getName());
        existing.setDistrict(farm.getDistrict());
        existing.setLocationLat(farm.getLocationLat());
        existing.setLocationLng(farm.getLocationLng());
        existing.setDescription(farm.getDescription());

        return farmRepository.save(existing);
    }

    @Override
    public void deleteFarm(Long id) {
        farmRepository.deleteById(id);
    }

    @Override
    public Farm getFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finca no encontrada"));
    }

    private void validateFarm(Farm farm) {

        if (farm.getLocationLat() != null) {
            if (farm.getLocationLat() < -90 || farm.getLocationLat() > 90) {
                throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
            }
        }

        if (farm.getLocationLng() != null) {
            if (farm.getLocationLng() < -180 || farm.getLocationLng() > 180) {
                throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");
            }
        }
    }
}