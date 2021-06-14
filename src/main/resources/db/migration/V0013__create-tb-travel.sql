create table tb_travels (
    id varchar(255) primary key,
    destiny varchar(255) not null,
    end_date datetime not null,
    client_ip varchar(255) not null,
    user_agent varchar(255) not null,
    created_at datetime not null,
    card_id varchar(255) not null,

    constraint fk_travels_card foreign key (card_id) references tb_card (id)
) engine=InnoDB default charset=utf8mb4;