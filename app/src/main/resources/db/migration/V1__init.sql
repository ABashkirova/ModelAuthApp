create table users
(
    id            int not null primary key,
    login         varchar(10) unique,
    hash_password varchar(255),
    salt          varchar(255)
);

create table accesses
(
    id       int not null primary key,
    user_id  int,
    resource varchar(255),
    role     varchar(255),
    foreign key (user_id) references users (id)
);

create table user_sessions
(
    id         int not null primary key,
    start_date date,
    end_date   date,
    volume     int,
    access_id  int,
    foreign key (access_id) references accesses (id)
);

create index USER_RESOURCE_RESOURCE_ROLE_index on accesses (resource, role);