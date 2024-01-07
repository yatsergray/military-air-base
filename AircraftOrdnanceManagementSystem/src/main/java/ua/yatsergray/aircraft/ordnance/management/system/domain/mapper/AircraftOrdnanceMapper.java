package ua.yatsergray.aircraft.ordnance.management.system.domain.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ua.yatsergray.aircraft.ordnance.management.system.domain.dto.AircraftOrdnanceDto;
import ua.yatsergray.aircraft.ordnance.management.system.domain.entity.AircraftOrdnance;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AircraftOrdnanceMapper {

    @Mapping(target = "type", expression = "java(ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceType.valueOf(aircraftOrdnance.getType()))")
    @Mapping(target = "purpose", expression = "java(ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnancePurpose.valueOf(aircraftOrdnance.getPurpose()))")
    @Mapping(target = "homing", expression = "java(ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceHoming.valueOf(aircraftOrdnance.getHoming()))")
    @Named(value = "mapToAircraftOrdnanceDto")
    AircraftOrdnanceDto mapToAircraftOrdnanceDto(AircraftOrdnance aircraftOrdnance);

    @Mapping(target = "type", expression = "java(aircraftOrdnanceDto.getType().name())")
    @Mapping(target = "purpose", expression = "java(aircraftOrdnanceDto.getPurpose().name())")
    @Mapping(target = "homing", expression = "java(aircraftOrdnanceDto.getHoming().name())")
    AircraftOrdnance mapToAircraftOrdnance(AircraftOrdnanceDto aircraftOrdnanceDto);

    @IterableMapping(qualifiedByName = "mapToAircraftOrdnanceDto")
    List<AircraftOrdnanceDto> mapAllToAircraftOrdnanceDtoList(List<AircraftOrdnance> aircraftOrdnance);
}
