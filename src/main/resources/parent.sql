create table parent
(
    parentid   bigint       not null
        constraint parent_pkey
            primary key,
    birthday   date         not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null
);

