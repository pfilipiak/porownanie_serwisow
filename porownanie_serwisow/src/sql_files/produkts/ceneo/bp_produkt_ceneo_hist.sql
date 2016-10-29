create table bp_produkt_ceneo_hist(

id bigserial primary key, 

domain text,
keyword text,
landing_page text,
position byte,
total_traffic bigint,
vol bigint,
traffic_share numeric(15,4),

mounth bigint, 
year bigint,



data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);