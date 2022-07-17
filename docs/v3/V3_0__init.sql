CREATE TABLE public.account
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR NOT NULL UNIQUE,
    password      VARCHAR NOT NULL,
    mail          VARCHAR NOT NULL,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
ALTER SEQUENCE account_id_seq RESTART 100 INCREMENT BY 50;

CREATE TABLE public.movie
(
    id                BIGSERIAL UNIQUE PRIMARY KEY,
    title             VARCHAR NOT NULL,
    release_date      DATE,
    tmdb_id           BIGINT UNIQUE,
    imdb_id           VARCHAR UNIQUE,
    original_language VARCHAR,
    tagline           VARCHAR,
    overview          VARCHAR,
    budget            BIGINT,
    revenue           BIGINT,
    poster_url        VARCHAR,
    runtime           INTEGER,
    creation_date     TIMESTAMP default now(),
    update_date       TIMESTAMP default now()
);
ALTER SEQUENCE movie_id_seq RESTART 100 INCREMENT BY 50;

CREATE TABLE public.director
(
    id            BIGSERIAL PRIMARY KEY UNIQUE,
    name          VARCHAR       NOT NULL,
    forename      VARCHAR       NOT NULL,
    tmdb_id       BIGINT UNIQUE NOT NULL,
    poster_url    VARCHAR,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
ALTER SEQUENCE director_id_seq RESTART 100 INCREMENT BY 50;

CREATE TABLE public.actor
(
    id            BIGSERIAL PRIMARY KEY UNIQUE,
    name          VARCHAR       NOT NULL,
    forename      VARCHAR       NOT NULL,
    tmdb_id       BIGINT UNIQUE NOT NULL,
    poster_url    VARCHAR,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
ALTER SEQUENCE actor_id_seq RESTART 100 INCREMENT BY 50;

CREATE TABLE public.cast
(
    movie_id      BIGINT REFERENCES movie (id),
    actor_id      BIGINT REFERENCES actor (id),
    role          VARCHAR NOT NULL,
    role_order    INT     NOT NULL,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);

CREATE TABLE public.review
(
    id            BIGSERIAL PRIMARY KEY UNIQUE,
    grade         INT  NOT NULL
        CONSTRAINT interval_grade
            CHECK (grade >= 0 AND grade <= 10),
    content       TEXT NULL,
    movie_id      BIGINT UNIQUE REFERENCES movie (id) ON DELETE CASCADE,
    account_id    BIGINT UNIQUE REFERENCES account (id) ON DELETE CASCADE,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
ALTER SEQUENCE review_id_seq RESTART 100 INCREMENT BY 50;

CREATE TABLE public.genre
(
    id            BIGSERIAL PRIMARY KEY,
    type          VARCHAR NOT NULL UNIQUE,
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);
ALTER SEQUENCE genre_id_seq RESTART 100 INCREMENT BY 50;

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

CREATE TABLE public.account_genre
(
    account_id BIGINT REFERENCES account (id),
    genre_id   BIGINT REFERENCES genre (id),
    CONSTRAINT account_genre_pk PRIMARY KEY (account_id, genre_id)
);

CREATE TABLE public.account_movie
(
    account_id BIGINT REFERENCES account (id),
    movie_id   BIGINT REFERENCES movie (id),
    CONSTRAINT account_movie_pk PRIMARY KEY (account_id, movie_id)
);

CREATE TABLE public.account_actor
(
    account_id BIGINT REFERENCES account (id),
    actor_id   BIGINT REFERENCES actor (id),
    CONSTRAINT account_actor_pk PRIMARY KEY (account_id, actor_id)
);

CREATE TABLE public.account_director
(
    account_id  BIGINT REFERENCES account (id),
    director_id BIGINT REFERENCES director (id),
    CONSTRAINT account_director_pk PRIMARY KEY (account_id, director_id)
);


CREATE TABLE public.account_movie_metadata
(
    account_id  BIGINT REFERENCES account (id) ON DELETE CASCADE,
    movie_id    BIGINT REFERENCES movie (id),
    is_favorite BOOLEAN,
    is_to_watch BOOLEAN,
    CONSTRAINT account_movie_metadata_pk PRIMARY KEY (account_id, movie_id)
);
