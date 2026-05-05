package com.conectacampo.repository;

import com.conectacampo.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByDepartmentId(Long departmentId);
    Province findByName(String name);
}