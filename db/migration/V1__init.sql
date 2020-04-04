create table USER
(
    ID              INT auto_increment not null primary key,
    LOGIN           VARCHAR(10) unique,
    HASH_PASSWORD   VARCHAR(255),
    SALT            VARCHAR(255)
);

create table ROLE
(
    ID          INT auto_increment not null primary key,
    ROLE_NAME   VARCHAR(30) unique
);

create table RESOURCE
(
    ID      INT auto_increment not null primary key,
    PATH    VARCHAR(255)
);

create table ACCESS(
    ID              INT auto_increment not null primary key,
    USER_ID         INT,
    RESOURCE_ID     INT,
    ROLE_ID         INT,
    FOREIGN KEY (USER_ID) REFERENCES USER(ID),
    FOREIGN KEY (RESOURCE_ID) REFERENCES RESOURCE(ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)
);

create table USER_SESSION
(
    ID          INT auto_increment not null primary key,
    START_DATE  DATE,
    END_DATE    DATE,
    VOLUME      INT,
    ACCESS_ID   INT,
    FOREIGN KEY (ACCESS_ID) REFERENCES ACCESS(ID)
);