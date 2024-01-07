package com.example.military.air.base.management.system.model.mapper;

import com.example.military.air.base.management.system.model.dto.MilitaryAirBaseDto;
import com.example.military.air.base.management.system.model.dto.editable.MilitaryAirBaseEditableDto;
import com.example.military.air.base.management.system.model.entiny.MilitaryAirBase;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = AircraftMapper.class)
public interface MilitaryAirBaseMapper {

    @Mapping(source = "aircraft", target = "aircraftDtoList")
    @Named(value = "mapToMilitaryAirBaseEditableDto")
    MilitaryAirBaseDto mapToMilitaryAirBaseDto(MilitaryAirBase militaryAirBase);

    @Mapping(target = "aircraft", ignore = true)
    MilitaryAirBase mapToMilitaryAirBase(MilitaryAirBaseEditableDto militaryAirBaseEditableDto);

    MilitaryAirBaseEditableDto mapToMilitaryAirBaseEditableDto(MilitaryAirBase militaryAirBase);

    @IterableMapping(qualifiedByName = "mapToMilitaryAirBaseEditableDto")
    List<MilitaryAirBaseDto> mapAllToMilitaryAirBaseDtoList(List<MilitaryAirBase> militaryAirBases);
}
