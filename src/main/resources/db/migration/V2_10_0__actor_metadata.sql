ALTER TABLE public.actor
    ADD tmdb_id    BIGINT UNIQUE NOT NULL,
    ADD poster_url VARCHAR,
    DROP CONSTRAINT actor_name_forename_key;
