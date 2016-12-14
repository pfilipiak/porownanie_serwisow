create table bp_produkt_media_expert_konkurencja(

id bigserial primary key, 


domain text,
competitor_relevance numeric(4,2),
common int8,
organic_keywords int8,
organic_traffic int8
);
