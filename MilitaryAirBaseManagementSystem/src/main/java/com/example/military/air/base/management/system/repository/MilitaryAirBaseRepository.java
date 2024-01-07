package com.example.military.air.base.management.system.repository;

import com.example.military.air.base.management.system.model.entiny.MilitaryAirBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilitaryAirBaseRepository extends JpaRepository<MilitaryAirBase, Long> {

    @Query(value = "SELECT mab.id, mab.name, mab.location, mab.use, mab.type, mab.image_name, mab.image_type, " +
            "mab.image_data FROM military_air_bases mab INNER JOIN aircraft a ON mab.id = a.id_military_air_base " +
            "AND a.id = ?", nativeQuery = true)
    Optional<MilitaryAirBase> findByAircraftId(Long aircraftId);
}
