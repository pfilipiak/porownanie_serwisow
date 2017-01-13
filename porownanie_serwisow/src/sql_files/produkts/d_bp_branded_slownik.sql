create table d_bp_branded_slownik(
id bigserial primary key,
branded_fraze boolean,
opis text
);
insert into d_bp_branded_slownik(branded_fraze, opis) values
(true,'Brandowane'),
(false, 'Normalne'),
(null, 'Wszystkie');