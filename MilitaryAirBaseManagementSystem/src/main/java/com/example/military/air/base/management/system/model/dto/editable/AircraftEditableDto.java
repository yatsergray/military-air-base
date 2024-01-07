package com.example.military.air.base.management.system.model.dto.editable;

import com.example.military.air.base.management.system.model.dto.AircraftDto;
import com.example.military.air.base.management.system.model.type.AircraftCombatTask;
import com.example.military.air.base.management.system.model.type.AircraftPurpose;
import com.example.military.air.base.management.system.model.type.AircraftType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AircraftEditableDto extends AircraftDto {
    private Long id;
    private String model;
    private AircraftType type;
    private AircraftPurpose purpose;
    private AircraftCombatTask combatTask;
    private String imageName;
    private String imageType;
    private byte[] imageData;
    private Long militaryAirBaseId;
}
