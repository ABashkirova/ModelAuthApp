alter table user add column version int default 1;
alter table user_session add column version int default 1;
alter table access add column version int default 1;