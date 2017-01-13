create table d_bp_pozycja_zakres (
id bigserial primary key,
min_pozycja bigint,
max_pozycja bigint,
opis text);
insert into d_bp_pozycja_zakres (min_pozycja, max_pozycja, opis) values
(1,1, 'Top 1'),
(2,3, 'Top 2-3'),
(4,10, 'Top 4-10'),
(11, null, 'Powyzej 10'),
(null,null,'Wszystko');