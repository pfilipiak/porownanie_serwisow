create or replace function insert_hist_from_tmp(
    p_nazwa_tmp text,
    p_nazwa_hist text
)returns bigint as
$$
declare
    v_query text; 
begin
    v_query := 'insert into '||p_nazwa_hist|| '(keyword,position,search_volume,traffic_share,timestamp) select * from '||p_nazwa_hist;
    execute v_query;
    return 1;
end
$$
language 'plpgsql';
select insert_hist_from_tmp('tmp_euro_hist', 'bp_produkt_euro_history');

insert into bp_produkt_euro_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), to_timestamp("Timestamp,Frazy_2016_01_euro.com.pl-historical_domain_organic-p"::bigint), "Year"::int4,"Month"::int4 from tmp_euro_hist; 

insert into bp_produkt_media_expert_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_media_expert_hist; 

insert into bp_produkt_morele_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_morele_hist; 
insert into bp_produkt_oleole_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_oleole_hist; 
insert into bp_produkt_saturn_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic  Share":: numeric(4,2), to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_saturn_hist; 


insert into bp_produkt_media_markt_history (keyword,position,search_volume,url,traffic_share,timestamp,year,month) select "Keyword", "Position"::int4, "Search Volume"::bigint,"Url","Traffic Share":: numeric(4,2), to_timestamp("Timestamp"::bigint), "Year"::int4,"Month"::int4 from tmp_media_markt_hist; 