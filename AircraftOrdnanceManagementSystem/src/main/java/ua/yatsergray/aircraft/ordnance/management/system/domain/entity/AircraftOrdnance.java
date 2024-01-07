package ua.yatsergray.aircraft.ordnance.management.system.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "aircraft_ordnance")
public class AircraftOrdnance {

    @Id
    private String id;
    private String model;
    private Double weight;
    private String type;
    private String purpose;
    private String homing;
}
