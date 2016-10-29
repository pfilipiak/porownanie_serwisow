create or replace function f_bo_add_user2group(p_id_user_ins bigint, p_group_ins bigint, p_user bigint)
return bigint as
$$
declare
    v_wynik bigint;
begin
    perform 1
    from bo_group 
    where id = p_group_ins;

    if not found then
        return null;
    end if;

    perform 1
    from bo_user 
    where id = p_id_user_ins;

    if not found then
        return null;
    end if;

    insert into bo_operator2group (
    id_user,
    id_group,
    user_ins
    )
    values(
    )returning id into v_wynik;

    return v_wynik;

end
$$
language 'plpgsql';