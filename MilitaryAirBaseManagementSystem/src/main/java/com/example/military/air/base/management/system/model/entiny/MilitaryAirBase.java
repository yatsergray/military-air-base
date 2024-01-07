package com.example.military.air.base.management.system.model.entiny;

import com.example.military.air.base.management.system.model.type.MilitaryAirBaseType;
import com.example.military.air.base.management.system.model.type.MilitaryAirBaseUse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "military_air_bases")
public class MilitaryAirBase {

    @Id
    @SequenceGenerator(name = "military_air_bases_id_sequence", sequenceName = "military_air_bases_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "military_air_bases_id_sequence")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "use", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MilitaryAirBaseUse use;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MilitaryAirBaseType type;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @JdbcTypeCode(Types.LONGVARBINARY)
    @Column(name = "image_data")
    private byte[] imageData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "militaryAirBase", orphanRemoval = true)
    @ToString.Exclude
    private Set<Aircraft> aircraft = new LinkedHashSet<>();
}
