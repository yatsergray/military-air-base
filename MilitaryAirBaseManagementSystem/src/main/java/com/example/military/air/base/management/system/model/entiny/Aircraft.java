package com.example.military.air.base.management.system.model.entiny;

import com.example.military.air.base.management.system.model.type.AircraftCombatTask;
import com.example.military.air.base.management.system.model.type.AircraftPurpose;
import com.example.military.air.base.management.system.model.type.AircraftType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @SequenceGenerator(name = "aircraft_id_sequence", sequenceName = "aircraft_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_id_sequence")
    private Long id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AircraftType type;

    @Column(name = "purpose", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AircraftPurpose purpose;

    @Column(name = "combat_task", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AircraftCombatTask combatTask;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @JdbcTypeCode(Types.LONGVARBINARY)
    @Column(name = "image_data")
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_military_air_base")
    @ToString.Exclude
    private MilitaryAirBase militaryAirBase;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft aircraft)) return false;
        return Objects.equals(id, aircraft.id) && Objects.equals(model, aircraft.model) && type == aircraft.type && purpose == aircraft.purpose && combatTask == aircraft.combatTask && Objects.equals(imageName, aircraft.imageName) && Objects.equals(imageType, aircraft.imageType) && Arrays.equals(imageData, aircraft.imageData) && Objects.equals(militaryAirBase, aircraft.militaryAirBase);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, model, type, purpose, combatTask, imageName, imageType, militaryAirBase);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}
