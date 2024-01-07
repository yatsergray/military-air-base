package ua.yatsergray.aircraft.ordnance.management.system.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceHoming;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnancePurpose;
import ua.yatsergray.aircraft.ordnance.management.system.domain.type.AircraftOrdnanceType;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AircraftOrdnanceDto {
    private String id;
    private String model;
    private Double weight;
    private AircraftOrdnanceType type;
    private AircraftOrdnancePurpose purpose;
    private AircraftOrdnanceHoming homing;
}