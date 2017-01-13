
create table bp_produkt_euro_konkurencja(

id bigserial primary key, 


domain text,
competitor_relevance numeric(4,2),
common int8,
id_produkt_bigint int8,
organic_keywords int8,
organic_traffic int8
);


insert into bp_produkt_euro_konkurencja(domain, competitor_relevance, id_produkt_bigint, common, organic_keywords, organic_traffic)
values ('media_expert.pl',0.50,3,103,104,105 );
insert into bp_produkt_euro_konkurencja(domain, competitor_relevance, id_produkt_bigint, common, organic_keywords, organic_traffic)
values ('media_markt.pl',0.50,4,103,104,105 );
insert into bp_produkt_euro_konkurencja(domain, competitor_relevance, id_produkt_bigint, common, organic_keywords, organic_traffic)
values ('morele.net',0.50,5,103,104,105 );
insert into bp_produkt_euro_konkurencja(domain, competitor_relevance, id_produkt_bigint, common, organic_keywords, organic_traffic)
values ('saturn.pl',0.50,1,103,104,105 );
insert into bp_produkt_euro_konkurencja(domain, competitor_relevance, id_produkt_bigint, common, organic_keywords, organic_traffic)
values ('oleole.pl',0.50,6,103,104,105 );
