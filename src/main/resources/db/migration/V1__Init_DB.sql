create table contact
(
    id      bigint       not null auto_increment,
    user_id bigint       not null,
    name    varchar(255) not null,
    primary key (id)
) engine = InnoDB;


create table contact_emails
(
    contact_id bigint not null,
    email      varchar(255)
) engine = InnoDB;


create table contact_phone_numbers
(
    contact_id   bigint not null,
    phone_number varchar(255)
) engine = InnoDB;


create table user
(
    id       bigint       not null auto_increment,
    login    varchar(255) not null,
    password varchar(255) not null,
    role     enum ('USER'),
    primary key (id)
) engine = InnoDB;


alter table contact
    add constraint fk_contact_user_id
        foreign key (user_id)
            references user (id);

alter table contact_emails
    add constraint fk_contact_emails_contact_id
        foreign key (contact_id)
            references contact (id);

alter table contact_phone_numbers
    add constraint fk_contact_phone_numbers_contact_id
        foreign key (contact_id)
            references contact (id);