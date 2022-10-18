create table employee
(
    id         bigserial    not null,
    birth_date date         not null,
    email      varchar(255),
    first_name varchar(255),
    gender     varchar(255) not null,
    last_name  varchar(255),
    phone      varchar(255),
    primary key (id)
);
alter table employee
    add constraint UK_email unique (email);
