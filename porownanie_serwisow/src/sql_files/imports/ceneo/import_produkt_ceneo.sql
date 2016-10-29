create table import_produkt_ceneo(
id bigserial primary key,
id_paczka bigint references d_import_package(id) restrict update cascade delete cascade,

domain text,
keyword text,
landing_page text,
position byte,
total_traffic bigint,
volumen bigint,
traffic_share numeric(15,4),
live_data date,

data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);