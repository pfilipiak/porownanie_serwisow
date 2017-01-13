
create table bp_sum_count_staurn(
id bigserial primary key,
nazwa text,count text, sum text,mont text,year text,id_slownik_branded text,opis_slownik_branded text,id_slownik_pozycja text ,opis_slownik_pozycja text
);
create or replace function bp_calosc(
    p_opis text
)
returns  boolean as
$$
declare
    
    v_query text;
    v_row record; -- konkurenci
    v_row2 record; -- branded slownik
    v_row3 record; --pozycja zakres
    v_row4 record;
begin

    select * into v_row from d_bp_produkt where p_opis = recognize_text;
    
    
    for v_row2 in select *
        from d_bp_branded_slownik
        loop
        for v_row3 in select *
            from d_bp_pozycja_zakres
            loop
             v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, count(*) as count,sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                    || ' from ' || v_row.source_table_hist  
                    || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                    || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                    || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                    ||' group by  4,5 '; 
            raise notice 'zapytanie cud %',  v_query;
            for v_row4 in execute v_query
                loop
                    
                      

                    insert into bp_sum_count_staurn(nazwa ,count , sum ,mont ,year ,id_slownik_branded ,opis_slownik_branded ,id_slownik_pozycja ,opis_slownik_pozycja )
    values (v_row4.nazwa::text, v_row4.count::text, v_row4.sum::text,v_row4.month,  v_row4.year, v_row4.id_slownik_branded, v_row2.opis, v_row3.id, v_row3.opis);
                end loop;
        end loop;
    end loop;
----------- konkurencja
    v_query := 'select * from d_bp_produkt where id in (select id_produkt_bigint from '|| v_row.konkurencja_table || ')';
    
    for v_row in execute v_query    
        loop

        for v_row2 in select *
            from d_bp_branded_slownik
            loop
            for v_row3 in select *
                from d_bp_pozycja_zakres
                loop
                 v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, count(*) as count,sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                        || ' from ' || v_row.source_table_hist  
                        || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                        || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                        || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                        ||' group by  4,5 '; 
                raise notice 'zapytanie cud %',  v_query;
                for v_row4 in execute v_query
                    loop
                        

                    insert into bp_sum_count_staurn(nazwa ,count , sum ,mont ,year ,id_slownik_branded ,opis_slownik_branded ,id_slownik_pozycja ,opis_slownik_pozycja )
    values (v_row4.nazwa::text, v_row4.count::text, v_row4.sum::text,v_row4.month,  v_row4.year, v_row4.id_slownik_branded, v_row2.opis, v_row3.id, v_row3.opis);    
                      
                    end loop;
            end loop;
        end loop;
    end loop;

    return true;
end
$$
language plpgsql;
select bp_calosc('saturn.pl');


create table bp_compar_1_2_3_4_5_6(
id bigserial primary key,
keyword text,
search_volume_1 bigint,
position_1 bigint,
search_volume_2 bigint,
position_2 bigint,
search_volume_3 bigint,
position_3 bigint,
search_volume_4 bigint,
position_4 bigint,
search_volume_5 bigint,
position_5 bigint,
search_volume_6 bigint,
position_6 bigint
);
 
insert into bp_compar_1_2_3_4_5_6(keyword) select keyword from  bp_produkt_saturn where not brand_fraze
union 
select keyword from bp_produkt_euro where not brand_fraze
union 
select keyword from bp_produkt_media_expert where not brand_fraze
union 
select keyword from bp_produkt_media_markt where not brand_fraze
union 
select keyword from bp_produkt_morele where not brand_fraze
union 
select keyword from bp_produkt_oleole where not brand_fraze;

bp_produkt_saturn -1 
bp_produkt_euro -2 
bp_produkt_media_expert -3
bp_produkt_media_markt -4 
bp_produkt_morele -5 
bp_produkt_oleole -6

update bp_compar_1_2_3_4_5_6 com set search_volume_1 = bp.search_volume, position_1 = bp.position from bp_produkt_saturn bp where com.keyword = bp.keyword;
update bp_compar_1_2_3_4_5_6 com set search_volume_2 = bp.search_volume, position_2 = bp.position from bp_produkt_euro bp where com.keyword = bp.keyword;
update bp_compar_1_2_3_4_5_6 com set search_volume_3 = bp.search_volume, position_3 = bp.position from bp_produkt_media_expert bp where com.keyword = bp.keyword;
update bp_compar_1_2_3_4_5_6 com set search_volume_4 = bp.search_volume, position_4 = bp.position from bp_produkt_media_markt bp where com.keyword = bp.keyword;
update bp_compar_1_2_3_4_5_6 com set search_volume_5 = bp.search_volume, position_5 = bp.position from bp_produkt_morele bp where com.keyword = bp.keyword;
update bp_compar_1_2_3_4_5_6 com set search_volume_6 = bp.search_volume, position_6 = bp.position from bp_produkt_oleole bp where com.keyword = bp.keyword;

------------ current_ctr

Select (select name from d_bp_produkt where id = 1) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_saturn
union
Select (select name from d_bp_produkt where id = 2) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_euro
union
Select (select name from d_bp_produkt where id = 3) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_media_expert
union
Select (select name from d_bp_produkt where id = 4) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_media_markt
union
Select (select name from d_bp_produkt where id = 5) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_morele
union
Select (select name from d_bp_produkt where id = 6) as name, 
sum(month_1_current_ctr) as "m-12", sum(month_2_current_ctr) as "m-11", sum(month_3_current_ctr) as "m-10", sum(month_4_current_ctr) as "m-9", sum(month_5_current_ctr) as "m-8", sum(month_6_current_ctr) as "m-7", sum(month_7_current_ctr) as "m-6", sum(month_8_current_ctr) as "m-5", sum(month_9_current_ctr) as "m-4", sum(month_10_current_ctr) as "m-3", sum(month_11_current_ctr) as "m-2", sum(month_12_current_ctr) as "m-1" from bp_produkt_oleole;

------------- max_ctr

Select (select name from d_bp_produkt where id = 1) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_saturn
union
Select (select name from d_bp_produkt where id = 2) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_euro
union
Select (select name from d_bp_produkt where id = 3) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_media_expert
union
Select (select name from d_bp_produkt where id = 4) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_media_markt
union
Select (select name from d_bp_produkt where id = 5) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_morele
union
Select (select name from d_bp_produkt where id = 6) as name, 
sum(month_1_max_ctr) as "m-12", sum(month_2_max_ctr) as "m-11", sum(month_3_max_ctr) as "m-10", sum(month_4_max_ctr) as "m-9", sum(month_5_max_ctr) as "m-8", sum(month_6_max_ctr) as "m-7", sum(month_7_max_ctr) as "m-6", sum(month_8_max_ctr) as "m-5", sum(month_9_max_ctr) as "m-4", sum(month_10_max_ctr) as "m-3", sum(month_11_max_ctr) as "m-2", sum(month_12_max_ctr) as "m-1" from bp_produkt_oleole;

------roznica ctr  



Select (select name from d_bp_produkt where id = 1) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_saturn
union
Select (select name from d_bp_produkt where id = 2) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_euro
union
Select (select name from d_bp_produkt where id = 3) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_media_expert
union
Select (select name from d_bp_produkt where id = 4) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_media_markt
union
Select (select name from d_bp_produkt where id = 5) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_morele
union
Select (select name from d_bp_produkt where id = 6) as name, 
sum(month_1_roznica) as "m-12", sum(month_2_roznica) as "m-11", sum(month_3_roznica) as "m-10", sum(month_4_roznica) as "m-9", sum(month_5_roznica) as "m-8", sum(month_6_roznica) as "m-7", sum(month_7_roznica) as "m-6", sum(month_8_roznica) as "m-5", sum(month_9_roznica) as "m-4", sum(month_10_roznica) as "m-3", sum(month_11_roznica) as "m-2", sum(month_12_roznica) as "m-1" from bp_produkt_oleole;
