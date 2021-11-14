INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (3, 'Fake movie', '2001-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 9, 'Some text.', 3, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (1, 3, '2020-10-12');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (2, 3, '2020-10-18');

INSERT INTO movie_actor (movie_id, actor_id)
values (3, 1);

INSERT INTO movie_actor (movie_id, actor_id)
values (3, 2);

INSERT INTO movie_director (movie_id, director_id)
values (3, 1);

INSERT INTO movie_genre (movie_id, genre_id)
values (3, 1);

INSERT INTO movie_genre (movie_id, genre_id)
values (3, 3);

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (3, 1234, 'tt1111', 'en', 'A tagline!', 'An overview!', 9000, 14000, 'An URL', 123);
