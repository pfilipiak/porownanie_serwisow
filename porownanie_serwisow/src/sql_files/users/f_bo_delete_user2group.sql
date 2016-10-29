create or replace function f_bo_delete_user2group(p_id_user_del bigint, p_group_del bigint)
return boolean as
$$
declare
    v_id_row bigint;
begin
    select  id
    into v_id_row
    from bo_user2group 
    where id_user = p_id_user_del
    and id_group = p_group_del;

    if not found then
        return false;
    end if;

   delete from bo_user2group
   where id = v_id_row;

    return true;

end
$$
language 'plpgsql';