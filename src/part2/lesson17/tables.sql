CREATE SEQUENCE id_seq;

create table "USER"
(
    id          numeric default nextval('id_seq'::regclass) not null
        constraint "USER_pkey"
            primary key,
    name        text                                        not null,
    birthday    timestamp,
    "login_ID"  text                                        not null
        constraint lgnunq
            unique,
    city        text,
    email       text,
    description text
);

alter table "USER"
    owner to postgres;

create table "ROLE"
(
    id          numeric default nextval('id_seq'::regclass) not null
        constraint "ROLE_pkey"
            primary key,
    name        text                                        not null,
    description text
);

alter table "ROLE"
    owner to postgres;

create table "USER_ROLE"
(
    id      numeric default nextval('id_seq'::regclass) not null
        constraint "USER_ROLE_pkey"
            primary key,
    user_id numeric                                     not null
        constraint "USER_FK"
            references "USER",
    role_id numeric                                     not null
        constraint "ROLE_FK"
            references "ROLE"
);

alter table "USER_ROLE"
    owner to postgres;

create table APP_LOGS
(
    log_id     varchar(100) not null
        constraint app_logs_pkey
            primary key,
    entry_date timestamp,
    logger     varchar(100),
    log_level  varchar(100),
    message    text,
    exception  text
);

alter table APP_LOGS
    owner to postgres;





