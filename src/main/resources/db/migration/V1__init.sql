create table USER
(
    ID            int auto_increment not null primary key,
    LOGIN         varchar(10) unique,
    HASH_PASSWORD varchar(255),
    SALT          varchar(255)
);

create table ACCESS
(
    ID       int auto_increment not null primary key,
    USER_ID  int,
    RESOURCE varchar(255),
    ROLE     varchar(255),
    foreign key (USER_ID) references USER (ID)
);

create table USER_SESSION
(
    ID         int auto_increment not null primary key,
    START_DATE date,
    END_DATE   date,
    VOLUME     int,
    ACCESS_ID  int,
    foreign key (ACCESS_ID) references ACCESS (ID)
);

create index USER_RESOURCE_RESOURCE_ROLE_index on ACCESS (RESOURCE, ROLE);