package com.example.military.air.base.management.system.model.dto;

import com.example.military.air.base.management.system.model.type.MilitaryAirBaseType;
import com.example.military.air.base.management.system.model.type.MilitaryAirBaseUse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MilitaryAirBaseDto {
    private Long id;
    private String name;
    private String location;
    private MilitaryAirBaseUse use;
    private MilitaryAirBaseType type;
    private String imageName;
    private String imageType;
    private byte[] imageData;

    @JsonProperty("aircraft")
    private List<AircraftDto> aircraftDtoList;
}
