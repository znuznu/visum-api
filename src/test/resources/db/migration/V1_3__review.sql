-- /!\ This isn't a join table!
CREATE TABLE public.movie_review
(
    id            BIGSERIAL PRIMARY KEY UNIQUE,
    grade         INT  NOT NULL
        CONSTRAINT interval_grade
            CHECK (grade >= 0 AND grade <= 10),
    content       TEXT NULL,
    movie_id      BIGINT UNIQUE REFERENCES movie (id),
    creation_date TIMESTAMP default now(),
    update_date   TIMESTAMP default now()
);

ALTER SEQUENCE movie_review_id_seq RESTART 100 INCREMENT BY 50;
