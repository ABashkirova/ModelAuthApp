alter table USER add column VERSION int default 1;
alter table USER_SESSION add column VERSION int default 1;
alter table ACCESS add column VERSION int default 1;