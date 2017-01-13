create or replace function bp_get_sum4produkt_and_kompetition(
    p_opis text
)
returns setof tsum_setof as
$$
declare
    v_wynik tsum_setof;
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
             v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                    || ' from ' || v_row.source_table_hist  
                    || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                    || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                    || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                    ||' group by  3,4 '; 
            raise notice 'zapytanie cud %',  v_query;
            for v_row4 in execute v_query
                loop
                    v_wynik.nazwa := v_row4.nazwa;
                    v_wynik.sum := v_row4.sum;
                    v_wynik.month := v_row4.month;
                    v_wynik.year := v_row4.year;
                    v_wynik.id_slownik_pozycja := v_row4.id_slownik_pozycja;
                    v_wynik.id_slownik_branded := v_row4.id_slownik_branded;

                      

                    return next v_wynik;
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
                 v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                        || ' from ' || v_row.source_table_hist  
                        || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                        || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                        || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                        ||' group by  3,4 '; 
                raise notice 'zapytanie cud %',  v_query;
                for v_row4 in execute v_query
                    loop
                        v_wynik.nazwa := v_row4.nazwa;
                        v_wynik.sum := v_row4.sum;
                        v_wynik.month := v_row4.month;
                        v_wynik.year := v_row4.year;
                        v_wynik.id_slownik_pozycja := v_row4.id_slownik_pozycja;
                        v_wynik.id_slownik_branded := v_row4.id_slownik_branded;



                        return next v_wynik;
                    end loop;
            end loop;
        end loop;
    end loop;


   -- return next v_wynik;
end
$$
language plpgsql;

--select bp_get_sum4produkt_and_kompetition('saturn.pl');

create or replace function bp_get_sum4produkt_and_kompetition(
    p_opis text,
    p_id_slownik_pozycja bigint,
    p_id_slownik_branded bigint
)
returns setof tsum_setof as
$$
declare
    v_wynik tsum_setof;
    v_query text;
    v_row record; -- konkurenci
    v_row2 record; -- branded slownik
    v_row3 record; --pozycja zakres
    v_row4 record;
begin

    select * into v_row from d_bp_produkt where p_opis = recognize_text;
    
    
    for v_row2 in select *
        from d_bp_branded_slownik
        where id = p_id_slownik_branded
        loop
        
        for v_row3 in select *
            from d_bp_pozycja_zakres
            where id = p_id_slownik_pozycja
            loop
             v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                    || ' from ' || v_row.source_table_hist  
                    || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                    || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                    || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                    ||' group by  3,4 '; 
            raise notice 'zapytanie cud %',  v_query;
            for v_row4 in execute v_query
                loop
                    v_wynik.nazwa := v_row4.nazwa;
                    v_wynik.sum := v_row4.sum;
                    v_wynik.month := v_row4.month;
                    v_wynik.year := v_row4.year;
                    v_wynik.id_slownik_pozycja := v_row4.id_slownik_pozycja;
                    v_wynik.id_slownik_branded := v_row4.id_slownik_branded;

                      

                    return next v_wynik;
                end loop;
        end loop;
    end loop;
----------- konkurencja
    v_query := 'select * from d_bp_produkt where id in (select id_produkt_bigint from '|| v_row.konkurencja_table || ')';
    
    for v_row in execute v_query    
        loop

        for v_row2 in select *
            from d_bp_branded_slownik
            where id = p_id_slownik_branded
            loop
            for v_row3 in select *
                from d_bp_pozycja_zakres
                where id = p_id_slownik_pozycja
                loop
                 v_query := 'select '||E'\'' || v_row.recognize_text ||E'\''||' as nazwa, sum(search_volume) as sum, month, year, '|| v_row2.id || ' as id_slownik_branded ,' ||v_row3.id|| ' as id_slownik_pozycja ' 
                        || ' from ' || v_row.source_table_hist  
                        || ' where' || case when v_row2.branded_fraze is not null then ' brand_fraze = ' ||v_row2.branded_fraze || ' and ' else ' true and ' end
                        || case when v_row3.min_pozycja is not null then ' position >= ' ||v_row3.min_pozycja|| ' and ' else ' true and 'end
                        || case when v_row3.max_pozycja is not null then ' position <= ' ||v_row3.min_pozycja|| '  ' else ' true  'end
                        ||' group by  3,4 '; 
                raise notice 'zapytanie cud %',  v_query;
                for v_row4 in execute v_query
                    loop
                        v_wynik.nazwa := v_row4.nazwa;
                        v_wynik.sum := v_row4.sum;
                        v_wynik.month := v_row4.month;
                        v_wynik.year := v_row4.year;
                        v_wynik.id_slownik_pozycja := v_row4.id_slownik_pozycja;
                        v_wynik.id_slownik_branded := v_row4.id_slownik_branded;



                        return next v_wynik;
                    end loop;
            end loop;
        end loop;
    end loop;


   -- return next v_wynik;
end
$$
language plpgsql;


select bp_get_sum4produkt_and_kompetition('saturn.pl',1,1);