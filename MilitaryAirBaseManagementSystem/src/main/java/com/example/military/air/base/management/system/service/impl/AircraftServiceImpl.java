package com.example.military.air.base.management.system.service.impl;

import com.example.military.air.base.management.system.exception.NoSuchAircraftException;
import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.AircraftDto;
import com.example.military.air.base.management.system.model.dto.editable.AircraftEditableDto;
import com.example.military.air.base.management.system.model.entiny.Aircraft;
import com.example.military.air.base.management.system.model.entiny.MilitaryAirBase;
import com.example.military.air.base.management.system.model.mapper.AircraftMapper;
import com.example.military.air.base.management.system.repository.AircraftRepository;
import com.example.military.air.base.management.system.repository.MilitaryAirBaseRepository;
import com.example.military.air.base.management.system.service.AircraftService;
import com.example.military.air.base.management.system.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class AircraftServiceImpl implements AircraftService {

    @Value("${no.such.aircraft.exception.massage}")
    private String noSuchAircraftExceptionMessage;
    private final AircraftMapper aircraftMapper;
    private final AircraftRepository aircraftRepository;
    private final MilitaryAirBaseRepository militaryAirBaseRepository;

    @Autowired
    public AircraftServiceImpl(AircraftMapper aircraftMapper, AircraftRepository aircraftRepository, MilitaryAirBaseRepository militaryAirBaseRepository) {
        this.aircraftMapper = aircraftMapper;
        this.aircraftRepository = aircraftRepository;
        this.militaryAirBaseRepository = militaryAirBaseRepository;
    }

    @Override
    public AircraftDto addAircraft(AircraftEditableDto aircraftEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, IOException {
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findById(aircraftEditableDto.getMilitaryAirBaseId())
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchAircraftExceptionMessage, aircraftEditableDto.getMilitaryAirBaseId())));
        Aircraft aircraft = aircraftMapper.mapToAircraft(aircraftEditableDto);

        aircraft.setMilitaryAirBase(militaryAirBase);

        aircraft.setImageName(imageFile.getOriginalFilename());
        aircraft.setImageType(imageFile.getContentType());
        aircraft.setImageData(ImageUtils.compressImage(imageFile.getBytes()));

        return aircraftMapper.mapToAircraftDto(aircraftRepository.save(aircraft));
    }

    @Override
    public Optional<AircraftDto> getAircraftById(Long id) throws NoSuchAircraftException, DataFormatException, IOException {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new NoSuchAircraftException(String.format(noSuchAircraftExceptionMessage, id)));

        aircraft.setImageData(ImageUtils.decompressImage(aircraft.getImageData()));

        return Optional.ofNullable(aircraftMapper.mapToAircraftDto(aircraft));
    }

    @Override
    public Optional<AircraftEditableDto> getAircraftEditableById(Long id) throws NoSuchAircraftException, DataFormatException, IOException {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new NoSuchAircraftException(String.format(noSuchAircraftExceptionMessage, id)));

        aircraft.setImageData(ImageUtils.decompressImage(aircraft.getImageData()));

        return Optional.ofNullable(aircraftMapper.mapToAircraftEditableDto(aircraft));
    }

    @Override
    public List<AircraftDto> getAllAircraft() {
        return aircraftMapper.mapAllToAircraftDtoList(aircraftRepository.findAll());
    }

    @Override
    public AircraftDto modifyAircraftById(Long id, AircraftEditableDto aircraftEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, NoSuchAircraftException, IOException {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new NoSuchAircraftException(String.format("Aircraft does not exist with id=%d", id)));
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findById(aircraftEditableDto.getMilitaryAirBaseId())
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchAircraftExceptionMessage, aircraftEditableDto.getMilitaryAirBaseId())));

        aircraft.setModel(aircraftEditableDto.getModel());
        aircraft.setType(aircraftEditableDto.getType());
        aircraft.setPurpose(aircraftEditableDto.getPurpose());
        aircraft.setCombatTask(aircraftEditableDto.getCombatTask());
        aircraft.setMilitaryAirBase(militaryAirBase);

        if (!imageFile.isEmpty()) {
            aircraft.setImageName(imageFile.getOriginalFilename());
            aircraft.setImageType(imageFile.getContentType());
            aircraft.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        }

        return aircraftMapper.mapToAircraftDto(aircraftRepository.save(aircraft));
    }

    @Override
    public void removeAircraftById(Long id) throws NoSuchAircraftException {
        if (!aircraftRepository.existsById(id)) {
            throw new NoSuchAircraftException(String.format(noSuchAircraftExceptionMessage, id));
        }

        aircraftRepository.deleteById(id);
    }
}
