create table bp_produkt_media_markt(

id bigserial primary key, 


keyword text,
position int4,
search_volume int8,
url text,
traffic_share numeric(4,2),
trends text,
timestamp timestamp,
year int4,
month int4
);
