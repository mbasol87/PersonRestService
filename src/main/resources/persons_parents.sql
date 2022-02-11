create table persons_parents
(
    personid bigint not null
        constraint fkjvvg9ijgrlksu0h3uwu9qs7hk
            references person,
    parentid bigint not null
        constraint fkmcifs19d35ga28wryuxntvp24
            references parent
);


