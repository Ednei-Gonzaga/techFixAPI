drop table support_contacts;

create table support_contacts
(
    id          bigserial primary key,
    type        varchar(50)  not null,
    contact     varchar(100) not null unique,
    description varchar(100) not null,
    created_at   timestamp    not null
);



