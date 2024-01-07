package ua.yatsergray.aircraft.ordnance.management.system.service;

import ua.yatsergray.aircraft.ordnance.management.system.domain.dto.AircraftOrdnanceDto;
import ua.yatsergray.aircraft.ordnance.management.system.exception.NoSuchAircraftOrdnanceException;

import java.util.List;
import java.util.Optional;

public interface AircraftOrdnanceService {

    AircraftOrdnanceDto addAircraftOrdnance(AircraftOrdnanceDto aircraftOrdnanceDto);

    Optional<AircraftOrdnanceDto> getAircraftOrdnanceById(String id) throws NoSuchAircraftOrdnanceException;

    List<AircraftOrdnanceDto> getAllAircraftOrdnance();

    AircraftOrdnanceDto modifyAircraftOrdnanceById(String id, AircraftOrdnanceDto aircraftOrdnanceDto) throws NoSuchAircraftOrdnanceException;

    void removeAircraftOrdnanceById(String id) throws NoSuchAircraftOrdnanceException;
}
