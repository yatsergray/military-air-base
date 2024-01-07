package ua.yatsergray.aircraft.ordnance.management.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.yatsergray.aircraft.ordnance.management.system.domain.dto.AircraftOrdnanceDto;
import ua.yatsergray.aircraft.ordnance.management.system.domain.mapper.AircraftOrdnanceMapper;
import ua.yatsergray.aircraft.ordnance.management.system.exception.NoSuchAircraftOrdnanceException;
import ua.yatsergray.aircraft.ordnance.management.system.repository.AircraftOrdnanceRepository;
import ua.yatsergray.aircraft.ordnance.management.system.service.AircraftOrdnanceService;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftOrdnanceServiceImpl implements AircraftOrdnanceService {

    @Value("${no.such.aircraft.ordnance.exception.massage}")
    private String noSuchAircraftOrdnanceExceptionMessage;
    private final AircraftOrdnanceMapper aircraftOrdnanceMapper;
    private final AircraftOrdnanceRepository aircraftOrdnanceRepository;

    @Autowired
    public AircraftOrdnanceServiceImpl(AircraftOrdnanceMapper aircraftOrdnanceMapper, AircraftOrdnanceRepository aircraftOrdnanceRepository) {
        this.aircraftOrdnanceMapper = aircraftOrdnanceMapper;
        this.aircraftOrdnanceRepository = aircraftOrdnanceRepository;
    }

    @Override
    public AircraftOrdnanceDto addAircraftOrdnance(AircraftOrdnanceDto aircraftOrdnanceDto) {
        return aircraftOrdnanceMapper.mapToAircraftOrdnanceDto(aircraftOrdnanceRepository.save(aircraftOrdnanceMapper.mapToAircraftOrdnance(aircraftOrdnanceDto)));
    }

    @Override
    public Optional<AircraftOrdnanceDto> getAircraftOrdnanceById(String id) throws NoSuchAircraftOrdnanceException {
        return Optional.ofNullable(aircraftOrdnanceRepository.findById(id)
                .map(aircraftOrdnanceMapper::mapToAircraftOrdnanceDto)
                .orElseThrow(() -> new NoSuchAircraftOrdnanceException(String.format(noSuchAircraftOrdnanceExceptionMessage, id))));
    }

    @Override
    public List<AircraftOrdnanceDto> getAllAircraftOrdnance() {
        return aircraftOrdnanceMapper.mapAllToAircraftOrdnanceDtoList(aircraftOrdnanceRepository.findAll());
    }

    @Override
    public AircraftOrdnanceDto modifyAircraftOrdnanceById(String id, AircraftOrdnanceDto aircraftOrdnanceDto) throws NoSuchAircraftOrdnanceException {
        return aircraftOrdnanceRepository.findById(id)
                .map(aircraftOrdnance -> {
                    aircraftOrdnance.setModel(aircraftOrdnanceDto.getModel());
                    aircraftOrdnance.setWeight(aircraftOrdnanceDto.getWeight());
                    aircraftOrdnance.setType(aircraftOrdnanceDto.getType().name());
                    aircraftOrdnance.setPurpose(aircraftOrdnanceDto.getPurpose().name());
                    aircraftOrdnance.setHoming(aircraftOrdnanceDto.getHoming().name());

                    return aircraftOrdnanceMapper.mapToAircraftOrdnanceDto(aircraftOrdnanceRepository.save(aircraftOrdnance));
                })
                .orElseThrow(() -> new NoSuchAircraftOrdnanceException(String.format(noSuchAircraftOrdnanceExceptionMessage, id)));
    }

    @Override
    public void removeAircraftOrdnanceById(String id) throws NoSuchAircraftOrdnanceException {
        if (!aircraftOrdnanceRepository.existsById(id)) {
            throw new NoSuchAircraftOrdnanceException(String.format(noSuchAircraftOrdnanceExceptionMessage, id));
        }

        aircraftOrdnanceRepository.deleteById(id);
    }
}
