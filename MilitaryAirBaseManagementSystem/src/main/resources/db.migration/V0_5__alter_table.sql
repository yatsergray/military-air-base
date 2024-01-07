alter table if exists aircraft
    add constraint id_military_air_fk foreign key (id_military_air_base) references military_air_bases;