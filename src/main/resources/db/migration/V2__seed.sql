insert into ROLE (ID, ROLE_NAME)
values (1, 'READ');
insert into ROLE (ID, ROLE_NAME)
values (2, 'WRITE');
insert into ROLE (ID, ROLE_NAME)
values (3, 'EXECUTE');

insert into USER (ID, LOGIN, HASH_PASSWORD, SALT)
values (1, 'sasha', 'bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3', 'V9Me2nx');
insert into USER (ID, LOGIN, HASH_PASSWORD, SALT)
values (2, 'admin', 'e0feb157dadff817c0f11b48d0441e56b475a27289117c6cb1ca7fd0b108b13c', '6xInN0l');
insert into USER (ID, LOGIN, HASH_PASSWORD, SALT)
values (3, 'q', '2002c9e01093b6d8b7d3699d6b7bd1a5fb8200340b1275f52ae5794d59854849', '4bxdOU7');
insert into USER (ID, LOGIN, HASH_PASSWORD, SALT)
values (4, 'abcdefghij', 'd880929e469c4a2c19352f76460853be52ee581f7fcdd3097f86f670f690e910', 'TM36tOy');

insert into RESOURCE (ID, PATH)
values (1, 'A');
insert into RESOURCE (ID, PATH)
values (2, 'B');
insert into RESOURCE (ID, PATH)
values (3, 'A.AA');
insert into RESOURCE (ID, PATH)
values (4, 'A.AA.AAA');
insert into RESOURCE (ID, PATH)
values (5, 'A.B');
insert into RESOURCE (ID, PATH)
values (6, 'A.B.C');

insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (1, 1, 1, 1);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (2, 1, 3, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (3, 1, 4, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (4, 2, 2, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (5, 2, 5, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (6, 1, 5, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (7, 2, 6, 1);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (8, 3, 6, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (9, 3, 5, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (13, 2, 1, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (10, 3, 2, 1);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (15, 1, 2, 2);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (11, 3, 4, 1);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (12, 3, 1, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (14, 2, 3, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (16, 1, 5, 3);
insert into ACCESS (ID, USER_ID, RESOURCE_ID, ROLE_ID)
values (17, 1, 6, 3);