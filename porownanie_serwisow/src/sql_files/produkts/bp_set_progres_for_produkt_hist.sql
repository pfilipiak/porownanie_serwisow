
create or replace function bp_set_progres_for_produkt_hist(p_id_produkt bigint)
returns boolean as
$$
declare
    v_id_produkt bigint;
    v_nazwa_current text;
    v_nazwa_hist text;
    v_query text;
    v_query_updates text;
    v_row record;
    v_ctr_current numeric(4,2);
    v_ctr_max numeric(4,2);
    v_tab_trends numeric(4,2)[];
    v_current bigint;
    v_max bigint;
    v_roznica_ctr bigint;
    i bigint;
    v_sum_ctr numeric(4,2);
    v_mnoznik_vol numeric(4,2);
    v_trends text;
    v_query3 text;
    v_mont int4;
    v_year int4;
    v_position int4;
begin
    v_sum_ctr:= 0.00;
    select source_table, source_table_hist into v_nazwa_current, v_nazwa_hist from d_bp_produkt where id = p_id_produkt;
    v_query := 'select * from '|| v_nazwa_hist ||' where not brand_fraze' ;
    for v_row in 
        execute v_query
        loop
        v_query3 := 'select trends, month, year from '|| v_nazwa_current|| ' where keyword ='||E'\''|| v_row.keyword||E'\'';
        execute v_query3 into v_trends, v_mont, v_year;
        if v_trends is null then continue;
        end if;
        select coalesce (avg_ctr,0.01) into v_ctr_current from d_bp_ctr where id = v_row.position;
       
        
        v_tab_trends := string_to_array(v_trends, ',')::numeric(4,2)[];
        v_ctr_current := coalesce (v_ctr_current, 0.01);
        
        FOR i IN 1..12 LOOP
            v_sum_ctr := v_sum_ctr + v_tab_trends[i];
        END LOOP;
        v_position := case 
                            when v_row.year+1 = v_year and v_row.month = v_mont then 1
                            when (v_row.year+1 = v_year and v_row.month = v_mont-1) or (v_row.year = v_year and v_row.month = v_mont-11) then 2
                            when (v_row.year+1 = v_year and v_row.month = v_mont-2) or (v_row.year = v_year and v_row.month = v_mont-10) then 3
                            when (v_row.year+1 = v_year and v_row.month = v_mont-3) or (v_row.year = v_year and v_row.month = v_mont-9) then 4
                            when (v_row.year+1 = v_year and v_row.month = v_mont-4) or (v_row.year = v_year and v_row.month = v_mont-8) then 5
                            when (v_row.year+1 = v_year and v_row.month = v_mont-5) or (v_row.year = v_year and v_row.month = v_mont-7) then 6
                            when (v_row.year+1 = v_year and v_row.month = v_mont-6) or (v_row.year = v_year and v_row.month = v_mont-6) then 7
                            when (v_row.year+1 = v_year and v_row.month = v_mont-7) or (v_row.year = v_year and v_row.month = v_mont-5) then 8
                            when (v_row.year+1 = v_year and v_row.month = v_mont-8) or (v_row.year = v_year and v_row.month = v_mont-4) then 9
                            when (v_row.year+1 = v_year and v_row.month = v_mont-9) or (v_row.year = v_year and v_row.month = v_mont-3) then 10
                            when (v_row.year+1 = v_year and v_row.month = v_mont-10) or (v_row.year = v_year and v_row.month = v_mont-2) then 11
                            when (v_row.year+1 = v_year and v_row.month = v_mont-11) or (v_row.year = v_year and v_row.month = v_mont-1) then 12
                        end;

        v_mnoznik_vol := (12/ v_sum_ctr)::numeric(4,2);
        
            v_current := (v_row.search_volume * v_ctr_current * v_mnoznik_vol* v_tab_trends[v_position]/100)::bigint;

            

            v_query_updates := 'update ' ||v_nazwa_hist ||' set  real_trafic = '|| v_current|| 'where id =' || v_row.id;
            

            execute v_query_updates;

            
       

        v_sum_ctr := 0.00;
        

        

    end loop;

        return true;
end


$$
language 'plpgsql';
select  bp_set_progres_for_produkt_hist(1);