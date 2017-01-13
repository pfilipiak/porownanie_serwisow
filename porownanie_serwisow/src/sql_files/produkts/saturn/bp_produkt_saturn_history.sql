create table bp_produkt_saturn_history(

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
alter table bp_produkt_saturn_history add column brand_fraze boolean;
update bp_produkt_saturn_history set brand_fraze = case when keyword ~* 'saturn' then true else false end;