CREATE TABLE public.director
(
    id       BIGSERIAL PRIMARY KEY UNIQUE,
    name     VARCHAR NOT NULL,
    forename VARCHAR NOT NULL,
    UNIQUE (name, forename)
);

CREATE TABLE public.actor
(
    id       BIGSERIAL PRIMARY KEY UNIQUE,
    name     VARCHAR NOT NULL,
    forename VARCHAR NOT NULL,
    UNIQUE (name, forename)
);

CREATE TABLE public.movie
(
    id            BIGSERIAL UNIQUE PRIMARY KEY,
    title         VARCHAR NOT NULL,
    release_date  DATE,
    is_favorite   BOOLEAN,
    should_watch  BOOLEAN,
    creation_date TIMESTAMP default now(),
    UNIQUE (title, release_date)
);

CREATE TABLE public.movie_viewing_history
(
    id           BIGSERIAL UNIQUE PRIMARY KEY,
    movie_id     BIGINT REFERENCES movie (id) NOT NULL,
    viewing_date DATE
);

CREATE TABLE public.movie_actor
(
    movie_id BIGINT REFERENCES movie (id),
    actor_id BIGINT REFERENCES actor (id),
    CONSTRAINT movie_actor_pk PRIMARY KEY (movie_id, actor_id)
);

CREATE TABLE public.movie_director
(
    movie_id    BIGINT REFERENCES movie (id),
    director_id BIGINT REFERENCES director (id),
    CONSTRAINT movie_director_pk PRIMARY KEY (movie_id, director_id)
);

CREATE TABLE public.movie_genre
(
    movie_id BIGINT REFERENCES movie (id),
    genre_id BIGINT REFERENCES genre (id),
    CONSTRAINT movie_genre_pk PRIMARY KEY (movie_id, genre_id)
);

ALTER SEQUENCE movie_id_seq RESTART 100 INCREMENT BY 50;

ALTER SEQUENCE actor_id_seq RESTART 100 INCREMENT BY 50;

ALTER SEQUENCE director_id_seq RESTART 100 INCREMENT BY 50;

ALTER SEQUENCE movie_viewing_history_id_seq RESTART 100 INCREMENT BY 50;
