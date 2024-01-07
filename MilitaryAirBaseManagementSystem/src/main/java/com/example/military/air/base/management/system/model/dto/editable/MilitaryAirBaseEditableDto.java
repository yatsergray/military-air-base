package com.example.military.air.base.management.system.model.dto.editable;

import com.example.military.air.base.management.system.model.type.MilitaryAirBaseType;
import com.example.military.air.base.management.system.model.type.MilitaryAirBaseUse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MilitaryAirBaseEditableDto {
    private Long id;
    private String name;
    private String location;
    private MilitaryAirBaseUse use;
    private MilitaryAirBaseType type;
    private String imageName;
    private String imageType;
    private byte[] imageData;
}
