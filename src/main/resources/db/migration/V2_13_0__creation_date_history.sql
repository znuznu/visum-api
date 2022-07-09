ALTER TABLE public.movie_viewing_history
    ADD
        creation_date TIMESTAMP default now();
