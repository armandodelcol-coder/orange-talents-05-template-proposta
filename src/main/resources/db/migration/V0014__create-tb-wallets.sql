create table tb_wallets (
    id varchar(255) primary key,
    email varchar(255) not null,
    name varchar(255) not null,
    card_id varchar(255) not null,

    constraint fk_wallets_card foreign key (card_id) references tb_card (id)
) engine=InnoDB default charset=utf8mb4;