create table bo_group (
id bigserial primary key,
privilage_description text,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);