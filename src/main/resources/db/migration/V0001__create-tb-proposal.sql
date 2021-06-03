create table tb_proposal (
    id bigint primary key auto_increment,
    document varchar(255) not null,
    email varchar(255) not null,
    name varchar(255) not null,
    address varchar(255) not null,
    salary decimal(10,2) not null
) engine=InnoDB default charset=utf8mb4;