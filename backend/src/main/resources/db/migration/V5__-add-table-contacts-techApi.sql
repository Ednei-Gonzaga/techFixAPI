create table support_contacts(
    id bigint primary key,
    type varchar(50) not null,
    contact varchar(100) not null unique,
    description varchar(100) not null,
    crated_at timestamp not null
);