create or replace function f_import_ceneo(p_id_opr bigint)
returns bigint as 
$$
declare
    v_row rekord;
begin

    -- WARUNEK NA SKOPIOWANIE DANYCH DO HISTORYCZNEJ TABELI!!!!!!

    for v_row in 
            select * from import_produkt_ceneo 
                where id_status_import = 1
    loop
        -- tutaj środek funkcji łącznie z obsługą błędów

        update import_produkt_cene
        set id_status_import =2
        where id = v_row.id;
    end loop;
    return 1;
end
$$
language 'plpgsql';