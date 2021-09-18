CREATE TABLE public.account
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

-- Useless but who knows
ALTER SEQUENCE account_id_seq RESTART 100 INCREMENT BY 50;
