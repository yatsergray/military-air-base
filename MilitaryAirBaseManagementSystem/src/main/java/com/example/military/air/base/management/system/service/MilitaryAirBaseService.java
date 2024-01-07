package com.example.military.air.base.management.system.service;

import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.MilitaryAirBaseDto;
import com.example.military.air.base.management.system.model.dto.editable.MilitaryAirBaseEditableDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

public interface MilitaryAirBaseService {

    MilitaryAirBaseDto addMilitaryAirBase(MilitaryAirBaseEditableDto militaryAirBaseEditableDto, MultipartFile imageFile) throws IOException;

    Optional<MilitaryAirBaseDto> getMilitaryAirBaseById(Long id) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException;

    Optional<MilitaryAirBaseEditableDto> getMilitaryAirBaseEditableById(Long id) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException;

    Optional<MilitaryAirBaseDto> getMilitaryAirBaseByAircraftId(Long aircraftId) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException;

    List<MilitaryAirBaseDto> getAllMilitaryAirBases();

    MilitaryAirBaseDto modifyMilitaryAirBaseById(Long id, MilitaryAirBaseEditableDto militaryAirBaseEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, IOException;

    void removeMilitaryAirBaseById(Long id) throws NoSuchMilitaryAirBaseException;
}
