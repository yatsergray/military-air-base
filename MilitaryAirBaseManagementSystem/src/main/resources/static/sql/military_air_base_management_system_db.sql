create sequence military_air_bases_seq start with 1 increment by 1;
create sequence aircraft_seq start with 1 increment by 1;

create table military_air_bases
(
    id       bigint default nextval('military_air_bases_seq') not null,
    name     varchar(255)                                     not null,
    location varchar(255)                                     not null,
    type     varchar(255)                                     not null,
    use      varchar(255)                                     not null,
    primary key (id)
);

create table aircraft
(
    id                   bigint default nextval('aircraft_seq') not null,
    id_military_air_base bigint                                 not null,
    model                varchar(255)                           not null,
    type                 varchar(255)                           not null,
    purpose              varchar(255)                           not null,
    combat_task          varchar(255)                           not null,
    primary key (id)
);

alter table aircraft
    add constraint id_military_air_fk foreign key (id_military_air_base) references military_air_bases;

