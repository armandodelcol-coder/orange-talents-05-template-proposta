set foreign_key_checks = 0;

delete from tb_proposal;
delete from tb_card;
delete from tb_blockades;
delete from tb_travels;

set foreign_key_checks = 1;

alter table tb_proposal auto_increment = 1;
alter table tb_blockades auto_increment = 1;