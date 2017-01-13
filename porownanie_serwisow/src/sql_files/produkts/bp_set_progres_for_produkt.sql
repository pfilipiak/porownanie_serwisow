create or replace function bp_set_progres_for_produkt(p_id_produkt bigint)
returns boolean as
$$
declare
    v_id_produkt bigint;
    v_nazwa_current text;
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
begin
    v_sum_ctr:= 0.00;
    select source_table into v_nazwa_current from d_bp_produkt where id = p_id_produkt;
    v_query := 'select * from '|| v_nazwa_current ||' where not brand_fraze' ;
    for v_row in 
        execute v_query
        loop
        select coalesce (avg_ctr,0.01) into v_ctr_current from d_bp_ctr where id = v_row.position;
        select avg_ctr into v_ctr_max from d_bp_ctr where id = 1;
        v_tab_trends := string_to_array(v_row.trends, ',')::numeric(4,2)[];
        v_ctr_current := coalesce (v_ctr_current, 0.01);
        --raise notice 'wejdze 1';
        FOR i IN 1..12 LOOP
            v_sum_ctr := v_sum_ctr + v_tab_trends[i];
        END LOOP;
        --raise notice 'wejdze 2';
        v_mnoznik_vol := (12/ v_sum_ctr)::numeric(4,2);
        FOR i IN 1..12 LOOP
            v_current := (v_row.search_volume * v_ctr_current * v_mnoznik_vol* v_tab_trends[i]/100)::bigint;
            v_max:= (v_row.search_volume * v_ctr_max * v_mnoznik_vol* v_tab_trends[i]/100)::bigint;
            --raise notice 'search volume   %, ctr current %, ctr max %, mnoznik %', v_row.search_volume, v_ctr_current, v_ctr_max, v_mnoznik_vol;

            v_query_updates := 'update ' ||v_nazwa_current ||' set month_'||i||'_current_ctr = '|| v_current|| ', month_'||i||'_max_ctr = '|| v_max|| ', month_'||i||'_roznica = '|| v_max-v_current||' where id =' || v_row.id;
            --raise notice 'query ciekawe %', v_query_updates;

            execute v_query_updates;

            
       

        v_sum_ctr := 0.00;
        END LOOP;

        

    end loop;

        return true;
end


$$
language 'plpgsql';

select bp_set_progres_for_produkt(1);
select bp_set_progres_for_produkt(2);
select bp_set_progres_for_produkt(3);
select bp_set_progres_for_produkt(4);
select bp_set_progres_for_produkt(5);
select bp_set_progres_for_produkt(6);