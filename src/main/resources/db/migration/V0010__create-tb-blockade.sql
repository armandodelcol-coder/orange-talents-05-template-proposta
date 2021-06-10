create table tb_blockades (
    id bigint primary key auto_increment,
    client_ip varchar(255) not null,
    user_agent varchar(255) not null,
    created_at datetime not null,
    card_id varchar(255) not null,

    constraint fk_blockades_card foreign key (card_id) references tb_card (id)
) engine=InnoDB default charset=utf8mb4;