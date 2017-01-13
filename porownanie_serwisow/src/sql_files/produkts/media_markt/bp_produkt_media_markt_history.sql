create table bp_produkt_media_markt_history(

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
alter table bp_produkt_media_markt_history add column brand_fraze boolean;
update bp_produkt_media_markt_history set brand_fraze = case when (keyword ~* 'media_markt' or keyword ~* 'media markt' ) then true else false end;
