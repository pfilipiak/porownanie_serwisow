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
alter table bp_produkt_media_markt add column brand_fraze boolean;
update bp_produkt_media_markt set brand_fraze = case when (keyword ~* 'media_markt' or keyword ~* 'media markt' ) then true else false end;



alter table bp_produkt_media_markt add column month_1_current_ctr bigint;
alter table bp_produkt_media_markt add column month_1_max_ctr bigint;
alter table bp_produkt_media_markt add column month_1_roznica bigint;

alter table bp_produkt_media_markt add column month_2_current_ctr bigint;
alter table bp_produkt_media_markt add column month_2_max_ctr bigint;
alter table bp_produkt_media_markt add column month_2_roznica bigint;

alter table bp_produkt_media_markt add column month_3_current_ctr bigint;
alter table bp_produkt_media_markt add column month_3_max_ctr bigint;
alter table bp_produkt_media_markt add column month_3_roznica bigint;

alter table bp_produkt_media_markt add column month_4_current_ctr bigint;
alter table bp_produkt_media_markt add column month_4_max_ctr bigint;
alter table bp_produkt_media_markt add column month_4_roznica bigint;

alter table bp_produkt_media_markt add column month_5_current_ctr bigint;
alter table bp_produkt_media_markt add column month_5_max_ctr bigint;
alter table bp_produkt_media_markt add column month_5_roznica bigint;

alter table bp_produkt_media_markt add column month_6_current_ctr bigint;
alter table bp_produkt_media_markt add column month_6_max_ctr bigint;
alter table bp_produkt_media_markt add column month_6_roznica bigint;

alter table bp_produkt_media_markt add column month_7_current_ctr bigint;
alter table bp_produkt_media_markt add column month_7_max_ctr bigint;
alter table bp_produkt_media_markt add column month_7_roznica bigint;

alter table bp_produkt_media_markt add column month_8_current_ctr bigint;
alter table bp_produkt_media_markt add column month_8_max_ctr bigint;
alter table bp_produkt_media_markt add column month_8_roznica bigint;

alter table bp_produkt_media_markt add column month_9_current_ctr bigint;
alter table bp_produkt_media_markt add column month_9_max_ctr bigint;
alter table bp_produkt_media_markt add column month_9_roznica bigint;

alter table bp_produkt_media_markt add column month_10_current_ctr bigint;
alter table bp_produkt_media_markt add column month_10_max_ctr bigint;
alter table bp_produkt_media_markt add column month_10_roznica bigint;

alter table bp_produkt_media_markt add column month_11_current_ctr bigint;
alter table bp_produkt_media_markt add column month_11_max_ctr bigint;
alter table bp_produkt_media_markt add column month_11_roznica bigint;

alter table bp_produkt_media_markt add column month_12_current_ctr bigint;
alter table bp_produkt_media_markt add column month_12_max_ctr bigint;
alter table bp_produkt_media_markt add column month_12_roznica bigint;
