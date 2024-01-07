package com.example.military.air.base.management.system.service;

import com.example.military.air.base.management.system.exception.NoSuchAircraftException;
import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.AircraftDto;
import com.example.military.air.base.management.system.model.dto.editable.AircraftEditableDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

public interface AircraftService {

    AircraftDto addAircraft(AircraftEditableDto aircraftEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, IOException;

    Optional<AircraftDto> getAircraftById(Long id) throws NoSuchAircraftException, DataFormatException, IOException;

    Optional<AircraftEditableDto> getAircraftEditableById(Long id) throws NoSuchAircraftException, DataFormatException, IOException;

    List<AircraftDto> getAllAircraft();

    AircraftDto modifyAircraftById(Long id, AircraftEditableDto aircraftEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, NoSuchAircraftException, IOException;

    void removeAircraftById(Long id) throws NoSuchAircraftException;
}
