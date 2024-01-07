create table military_air_bases
(
    id         bigint default nextval('military_air_bases_seq') not null,
    name       varchar(255)                                     not null,
    location   varchar(255)                                     not null,
    type       varchar(255)                                     not null,
    use        varchar(255)                                     not null,
    image_name varchar(255),
    image_type varchar(255),
    image_data bytea,
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
    image_name           varchar(255),
    image_type           varchar(255),
    image_data           bytea,
    primary key (id)
);