alter table users add column version int default 1;
alter table user_sessions add column version int default 1;
alter table accesses add column version int default 1;