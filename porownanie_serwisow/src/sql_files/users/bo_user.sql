create table bo_user(
id bigserial primary key,
first_name text,
second_name text,
email text,
phone text,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);
