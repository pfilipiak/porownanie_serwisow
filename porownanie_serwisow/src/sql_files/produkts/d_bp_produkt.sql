create table d_bp_produkt(
id bigserial primary key,

name   text,
recognize_text text,
source_table text,
source_table_hist text,
import_function text,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);