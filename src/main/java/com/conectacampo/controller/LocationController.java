package com.conectacampo.controller;

import com.conectacampo.model.Department;
import com.conectacampo.model.Province;
import com.conectacampo.model.District;
import com.conectacampo.repository.DepartmentRepository;
import com.conectacampo.repository.ProvinceRepository;
import com.conectacampo.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {

    private final DepartmentRepository departmentRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/provinces/{departmentId}")
    public List<Province> getProvincesByDepartment(@PathVariable Long departmentId) {
        return provinceRepository.findByDepartmentId(departmentId);
    }

    @GetMapping("/districts/{provinceId}")
    public List<District> getDistrictsByProvince(@PathVariable Long provinceId) {
        return districtRepository.findByProvinceId(provinceId);
    }

    @GetMapping("/provinces/name/{name}")
    public Province getProvinceByName(@PathVariable String name) {
        return provinceRepository.findByName(name);
    }
}