package com.example.military.air.base.management.system.model.mapper;

import com.example.military.air.base.management.system.model.dto.AircraftDto;
import com.example.military.air.base.management.system.model.dto.editable.AircraftEditableDto;
import com.example.military.air.base.management.system.model.entiny.Aircraft;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AircraftMapper {

    @Named(value = "mapToAircraftDto")
    AircraftDto mapToAircraftDto(Aircraft aircraft);

    @Mapping(target = "militaryAirBase", ignore = true)
    Aircraft mapToAircraft(AircraftEditableDto aircraftEditableDto);

    @Mapping(target = "militaryAirBaseId", source = "militaryAirBase.id")
    AircraftEditableDto mapToAircraftEditableDto(Aircraft aircraft);

    @IterableMapping(qualifiedByName = "mapToAircraftDto")
    List<AircraftDto> mapAllToAircraftDtoList(List<Aircraft> aircraft);
}
