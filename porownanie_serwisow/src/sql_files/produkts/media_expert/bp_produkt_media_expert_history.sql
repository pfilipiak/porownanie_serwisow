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

alter table bp_produkt_media_expert_history add column brand_fraze boolean;
update bp_produkt_media_expert_history set brand_fraze = case when (keyword ~* 'media_expert' or keyword ~* 'media expert' or keyword ~* 'media_ekspert' or keyword ~* 'media ekspert' ) then true else false end;


alter table bp_produkt_media_expert_history add column real_trafic bigint;