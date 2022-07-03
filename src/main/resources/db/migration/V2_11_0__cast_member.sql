DROP TABLE public.movie_actor;

CREATE TABLE public.cast_member
(
    movie_id      BIGINT REFERENCES movie (id),
    actor_id      BIGINT REFERENCES actor (id),
    character     VARCHAR NOT NULL,
    role_order    INT     NOT NULL,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
