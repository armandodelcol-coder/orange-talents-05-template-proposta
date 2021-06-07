create table tb_card (
    id varchar(255) primary key,
    emission_date datetime not null,
    owner varchar(255) not null,
    credit_limit decimal(10,2) not null
) engine=InnoDB default charset=utf8mb4;

create table tb_card_due_date (
    id varchar(255) primary key,
    day tinyint unsigned not null,
    created_at datetime not null,
    card_id varchar(255) not null,

    constraint fk_due_date_card_number foreign key (card_id) references tb_card (id)
) engine=InnoDB default charset=utf8mb4;