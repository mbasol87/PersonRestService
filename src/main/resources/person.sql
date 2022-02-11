create table person
(
    personid   bigint       not null
        constraint person_pkey
            primary key,
    birthday   date         not null,
    first_name varchar(255) not null,
    gender     varchar(255) not null,
    last_name  varchar(255) not null,
    constraint ukc5agrp14o9g1211fita3fcb6j
        unique (first_name, last_name)
);


