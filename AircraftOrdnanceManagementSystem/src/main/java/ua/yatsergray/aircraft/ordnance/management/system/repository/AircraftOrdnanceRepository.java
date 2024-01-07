package ua.yatsergray.aircraft.ordnance.management.system.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.yatsergray.aircraft.ordnance.management.system.domain.entity.AircraftOrdnance;

@Repository
public interface AircraftOrdnanceRepository extends MongoRepository<AircraftOrdnance, String> {
}
