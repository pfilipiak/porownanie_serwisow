create or replace function insert_live_from_tmp(
    p_nazwa_tmp text,
    p_nazwa_live text
)returns bigint as
$$
declare
begin

end
$$
language 'plpgsql';

insert into bp_produkt_euro (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Shar":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_euro_live; 
insert into bp_produkt_media_expert (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_media_expert_live; 
insert into bp_produkt_media_markt (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_media_markt_live; 
insert into bp_produkt_morele (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_morele_live; 
insert into bp_produkt_oleole (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_oleole_live; 
insert into bp_produkt_saturn (keyword,position,search_volume,url,traffic_share,trends,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic (%)":: numeric(4,2), "Trends",to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_saturn_live; 