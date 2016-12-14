create table bp_produkt_euro_history(

id bigserial primary key, 


keyword text,
position int4,
search_volume int8,
url text,
traffic_share numeric(4,2),
timestamp timestamp
);
alter table bp_produkt_euro_history add column year int4;
alter table bp_produkt_euro_history add column month int4;