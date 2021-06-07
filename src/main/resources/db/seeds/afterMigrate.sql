set foreign_key_checks = 0;

delete from tb_proposal;
delete from tb_card;
delete from tb_card_due_date;

set foreign_key_checks = 1;

alter table tb_proposal auto_increment = 1;