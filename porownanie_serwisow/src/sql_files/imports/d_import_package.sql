create table d_import_package(
id bigserial primary key,
id_type_produkt bigint references d_bp_produkt(id) restrict update cascade delete cascade,
data_ins timestamp default now(),
is_history boolean,
user_ins bigint,
data_up timestamp,
user_up bigint
);