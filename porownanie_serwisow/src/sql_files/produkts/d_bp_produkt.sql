create table d_bp_produkt(
id bigserial primary key,

name   text,
recognize_text text,
source_table text,
source_table_hist text,

import_function text,
data_ins timestamp default now(),
user_ins bigint,
data_up timestamp,
user_up bigint
);
alter table d_bp_produkt add column konkurencja_table text;
insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist)
values 
('saturn','saturn.pl', 'bp_produkt_saturn', 'bp_produkt_saturn_history');
insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('euro','euro.com.pl', 'bp_produkt_euro', 'bp_produkt_euro_history', 'bp_produkt_euro_history');

insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('euro','media_expert.com.pl', 'bp_produkt_media_expert', 'bp_produkt_media_expert_history', 'bp_produkt_media_expert_history');
insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('media_markt','media_markt.com.pl', 'bp_produkt_media_markt', 'bp_produkt_media_markt_history', 'bp_produkt_media_markt_konkurencja');

insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('media_markt','media_markt.com.pl', 'bp_produkt_media_markt', 'bp_produkt_media_markt_history', 'bp_produkt_media_markt_konkurencja');


insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('morele','morele.net', 'bp_produkt_morele', 'bp_produkt_morele_history', 'bp_produkt_morele_konkurencja');

insert into d_bp_produkt(name, recognize_text, source_table, source_table_hist, konkurencja_table)
values 
('oleole','oleole.pl', 'bp_produkt_oleole', 'bp_produkt_oleole_history', 'bp_produkt_oleole_konkurencja');