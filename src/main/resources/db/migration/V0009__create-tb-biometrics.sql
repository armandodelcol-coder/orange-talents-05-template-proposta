create table tb_biometrics (
    identifier varchar(255) primary key,
    finger_print_base64 varchar(255) not null,
    card_id varchar(255) not null,

    constraint fk_biometrics_card foreign key (card_id) references tb_card (id)
) engine=InnoDB default charset=utf8mb4;