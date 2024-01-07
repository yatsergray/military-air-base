package com.example.military.air.base.management.system.repository;

import com.example.military.air.base.management.system.model.entiny.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
