insert into bp_produkt_saturn_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_saturn_zestawienie; 


insert into bp_produkt_oleole_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_oleole_zestawienie; 
insert into bp_produkt_media_expert_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_media_expert_zestawienie; 
insert into bp_produkt_media_markt_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_media_markt_zestwienie; 
insert into bp_produkt_euro_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_euro_zestawienie; 

insert into bp_produkt_morele_zestawienie (domain, time, traffic,keywords) select domain, time, traffic::int8, keywords::int4 from tmp_morele_zestawienie; 