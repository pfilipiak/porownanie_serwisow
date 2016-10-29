create table bo_user2group(
id bigserial primary key,
id_user bigint references bo_user(id) on update cascade delete cascade,
id_group bigint references bo_group(id) on update cascade delete cascade,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);