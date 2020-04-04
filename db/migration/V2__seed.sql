insert into ROLE (ID, ROLE_NAME)
values (1, 'READ');
insert into ROLE (ID, ROLE_NAME)
values (2, 'WRITE');
insert into ROLE (ID, ROLE_NAME)
values (3, 'EXECUTE');

insert into USER(LOGIN, HASH_PASSWORD, SALT)
values (
           select *
           from CSVREAD('src/main/resources/users.csv')
       );

insert into RESOURCE(ID, PATH)
values (
           select *
           from CSVREAD('src/main/resources/resource.csv')
       );

insert into ACCESS(USER_ID, RESOURCE_ID, ROLE_ID)
values (
           select *
           from CSVREAD('src/main/resources/accesses.csv')
       )