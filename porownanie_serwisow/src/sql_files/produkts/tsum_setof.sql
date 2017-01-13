create type tsum_setof as (
nazwa text,
sum bigint,
month bigint, 
year bigint,
id_slownik_pozycja bigint,
id_slownik_branded bigint
);