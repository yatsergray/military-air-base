package com.example.military.air.base.management.system.service.impl;

import com.example.military.air.base.management.system.exception.NoSuchMilitaryAirBaseException;
import com.example.military.air.base.management.system.model.dto.MilitaryAirBaseDto;
import com.example.military.air.base.management.system.model.dto.editable.MilitaryAirBaseEditableDto;
import com.example.military.air.base.management.system.model.entiny.MilitaryAirBase;
import com.example.military.air.base.management.system.model.mapper.MilitaryAirBaseMapper;
import com.example.military.air.base.management.system.repository.MilitaryAirBaseRepository;
import com.example.military.air.base.management.system.service.MilitaryAirBaseService;
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
public class MilitaryAirBaseServiceImpl implements MilitaryAirBaseService {

    @Value("${no.such.military.air.base.exception.massage}")
    private String noSuchMilitaryAirBaseExceptionMessage;

    @Value("${no.such.military.air.base.by.aircraft.exception.massage}")
    private String noSuchMilitaryAirBaseByAircraftExceptionMessage;
    private final MilitaryAirBaseMapper militaryAirBaseMapper;
    private final MilitaryAirBaseRepository militaryAirBaseRepository;

    @Autowired
    public MilitaryAirBaseServiceImpl(MilitaryAirBaseMapper militaryAirBaseMapper, MilitaryAirBaseRepository militaryAirBaseRepository) {
        this.militaryAirBaseMapper = militaryAirBaseMapper;
        this.militaryAirBaseRepository = militaryAirBaseRepository;
    }

    @Override
    public MilitaryAirBaseDto addMilitaryAirBase(MilitaryAirBaseEditableDto militaryAirBaseEditableDto, MultipartFile imageFile) throws IOException {
        militaryAirBaseEditableDto.setImageName(imageFile.getOriginalFilename());
        militaryAirBaseEditableDto.setImageType(imageFile.getContentType());
        militaryAirBaseEditableDto.setImageData(ImageUtils.compressImage(imageFile.getBytes()));

        return militaryAirBaseMapper.mapToMilitaryAirBaseDto(militaryAirBaseRepository.save(militaryAirBaseMapper.mapToMilitaryAirBase(militaryAirBaseEditableDto)));
    }

    @Override
    public Optional<MilitaryAirBaseDto> getMilitaryAirBaseById(Long id) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException {
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findById(id)
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchMilitaryAirBaseExceptionMessage, id)));

        militaryAirBase.setImageData(ImageUtils.decompressImage(militaryAirBase.getImageData()));

        return Optional.ofNullable(militaryAirBaseMapper.mapToMilitaryAirBaseDto(militaryAirBase));
    }

    @Override
    public Optional<MilitaryAirBaseEditableDto> getMilitaryAirBaseEditableById(Long id) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException {
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findById(id)
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchMilitaryAirBaseExceptionMessage, id)));

        militaryAirBase.setImageData(ImageUtils.decompressImage(militaryAirBase.getImageData()));

        return Optional.ofNullable(militaryAirBaseMapper.mapToMilitaryAirBaseEditableDto(militaryAirBase));
    }

    @Override
    public Optional<MilitaryAirBaseDto> getMilitaryAirBaseByAircraftId(Long aircraftId) throws NoSuchMilitaryAirBaseException, DataFormatException, IOException {
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findByAircraftId(aircraftId)
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchMilitaryAirBaseByAircraftExceptionMessage, aircraftId)));

        militaryAirBase.setImageData(ImageUtils.decompressImage(militaryAirBase.getImageData()));

        return Optional.ofNullable(militaryAirBaseMapper.mapToMilitaryAirBaseDto(militaryAirBase));
    }

    @Override
    public List<MilitaryAirBaseDto> getAllMilitaryAirBases() {
        return militaryAirBaseMapper.mapAllToMilitaryAirBaseDtoList(militaryAirBaseRepository.findAll());
    }

    @Override
    public MilitaryAirBaseDto modifyMilitaryAirBaseById(Long id, MilitaryAirBaseEditableDto militaryAirBaseEditableDto, MultipartFile imageFile) throws NoSuchMilitaryAirBaseException, IOException {
        MilitaryAirBase militaryAirBase = militaryAirBaseRepository.findById(id)
                .orElseThrow(() -> new NoSuchMilitaryAirBaseException(String.format(noSuchMilitaryAirBaseExceptionMessage, id)));

        militaryAirBase.setName(militaryAirBaseEditableDto.getName());
        militaryAirBase.setLocation(militaryAirBaseEditableDto.getLocation());
        militaryAirBase.setUse(militaryAirBaseEditableDto.getUse());
        militaryAirBase.setType(militaryAirBaseEditableDto.getType());

        if (!imageFile.isEmpty()) {
            militaryAirBase.setImageName(imageFile.getOriginalFilename());
            militaryAirBase.setImageType(imageFile.getContentType());
            militaryAirBase.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        }

        return militaryAirBaseMapper.mapToMilitaryAirBaseDto(militaryAirBaseRepository.save(militaryAirBase));
    }

    @Override
    public void removeMilitaryAirBaseById(Long id) throws NoSuchMilitaryAirBaseException {
        if (!militaryAirBaseRepository.existsById(id)) {
            throw new NoSuchMilitaryAirBaseException(String.format(noSuchMilitaryAirBaseExceptionMessage, id));
        }

        militaryAirBaseRepository.deleteById(id);
    }
}
