-- /!\ This isn't a join table!
CREATE TABLE public.movie_metadata
(
    movie_id                BIGINT UNIQUE REFERENCES movie (id) ON DELETE CASCADE,
    tmdb_id                 BIGINT UNIQUE,
    imdb_id                 VARCHAR UNIQUE,
    original_language       VARCHAR,
    tagline                 VARCHAR,
    overview                VARCHAR,
    budget                  BIGINT,
    revenue                 BIGINT,
    poster_url              VARCHAR,
    runtime                 INTEGER
);
