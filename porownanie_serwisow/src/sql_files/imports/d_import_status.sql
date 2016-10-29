create table d_import_status(
id bigserial primary key,
opis text,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);