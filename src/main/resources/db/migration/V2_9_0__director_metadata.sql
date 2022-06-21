ALTER TABLE public.director
ADD tmdb_id     BIGINT UNIQUE NOT NULL,
ADD poster_url  VARCHAR,
DROP CONSTRAINT director_name_forename_key;

ALTER TABLE public.movie
DROP CONSTRAINT movie_title_release_date_key;
