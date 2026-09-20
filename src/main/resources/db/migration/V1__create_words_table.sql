create table words (
    id BIGSERIAL PRIMARY KEY,
    word varchar(50) not null,
    language varchar(10) not null,
    category varchar(50),
    difficulty varchar(20),
    active boolean not null default true,
    created_at timestamp not null default current_timestamp
);