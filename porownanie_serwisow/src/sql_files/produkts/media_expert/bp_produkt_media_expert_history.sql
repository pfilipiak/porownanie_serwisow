create table bp_produkt_media_expert_history(

id bigserial primary key, 


keyword text,
position int4,
search_volume int8,
url text,
traffic_share numeric(4,2),
timestamp timestamp,
year int4,
month int4
);
