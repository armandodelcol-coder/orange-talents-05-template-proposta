set foreign_key_checks = 0;

delete from tb_proposal;
delete from tb_card;

set foreign_key_checks = 1;

alter table tb_proposal auto_increment = 1;