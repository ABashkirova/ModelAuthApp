insert into USER (ID, LOGIN, HASH_PASSWORD, SALT)
values (1, 'sasha', 'bc4725cd5915a9cda45d2835bdd8e444be15c7c9aabdd0dc8693d7a7d2500dc3', 'V9Me2nx'),
       (2, 'admin', 'e0feb157dadff817c0f11b48d0441e56b475a27289117c6cb1ca7fd0b108b13c', '6xInN0l'),
       (3, 'q', '2002c9e01093b6d8b7d3699d6b7bd1a5fb8200340b1275f52ae5794d59854849', '4bxdOU7'),
       (4, 'abcdefghij', 'd880929e469c4a2c19352f76460853be52ee581f7fcdd3097f86f670f690e910', 'TM36tOy');


insert into ACCESS (ID, USER_ID, RESOURCE, ROLE)
values (1, 1, 'A', 'READ'),
       (2, 1, 'A.AA', 'WRITE'),
       (3, 1, 'A.AA.AAA', 'EXECUTE'),
       (4, 2, 'B', 'EXECUTE'),
       (5, 2, 'A.B', 'WRITE'),
       (6, 1, 'A.B', 'WRITE'),
       (7, 2, 'A.B.C', 'READ'),
       (8, 3, 'A.B.C', 'WRITE'),
       (9, 3, 'A.B', 'EXECUTE'),
       (13, 2, 'A', 'WRITE'),
       (10, 3, 'B', 'READ'),
       (15, 1, 'B', 'WRITE'),
       (11, 3, 'A.AA.AAA', 'READ'),
       (12, 3, 'A', 'EXECUTE'),
       (14, 2, 'A.AA', 'EXECUTE'),
       (16, 1, 'A.B', 'EXECUTE'),
       (17, 1, 'A.B.C', 'EXECUTE');