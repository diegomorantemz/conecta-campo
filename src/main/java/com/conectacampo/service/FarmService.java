package com.conectacampo.service;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import java.util.List;

public interface FarmService {
    List<Farm> getFarmsByUser(User user);
    Farm saveFarm(Farm farm, User user);
    Farm updateFarm(Long id, Farm farm, User user);
    void deleteFarm(Long id);
    Farm getFarmById(Long id);
}