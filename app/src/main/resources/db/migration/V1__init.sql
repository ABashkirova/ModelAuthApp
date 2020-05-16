create table user
(
    id            int primary key,
    login         varchar(10) unique,
    hash_password varchar(255),
    salt          varchar(255)
);

create table access
(
    id       int primary key,
    user_id  int,
    resource varchar(255),
    role     varchar(255),
    foreign key (user_id) references user (id)
);

create table user_session
(
    id         int primary key,
    start_date date,
    end_date   date,
    volume     int,
    access_id  int,
    foreign key (access_id) references access (id)
);

create index user_resource_role_index on access (resource, role);