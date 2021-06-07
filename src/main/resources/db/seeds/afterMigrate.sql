set foreign_key_checks = 0;

/* Deletar tudo nas tabelas */
delete from tb_proposal;
delete from tb_card;
delete from tb_card_due_date;

set foreign_key_checks = 1;

/* Resetar os auto_increments */
/* Ex: alter table tb_proposal auto_increment = 1; */
alter table tb_proposal auto_increment = 1;

/* Popular... */