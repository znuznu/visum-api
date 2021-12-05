INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (30, 'Fake movie with review 30', '2001-10-12', FALSE, FALSE, '2021-10-26T15:54:33Z');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (30, 1616, 'tt1616', 'jp', 'A tagline!', 'An overview!', 16000, 160000, 'An URL 30', 161);

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 3, 'Some text for movie 30.', 30, '2021-10-26T15:54:33Z', '2021-10-26T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (20, 'Fake movie with review 20', '2001-10-12', TRUE, FALSE, '2021-10-26T15:54:33Z');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (20, 2020, 'tt2020', 'ko', 'A tagline!', 'An overview!', 20000, 200000, 'An URL 20', 201);

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (2, 2, 'Some text for movie 20.', 20, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (33, 'Fake movie with review 33', '2001-10-12', FALSE, TRUE, '2021-10-26T15:54:33Z');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (33, 3333, 'tt3333', 'en', 'A tagline!', 'An overview!', 33000, 330000, 'An URL 33', 331);

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (3, 5, 'Some text for movie 33.', 33, '2021-10-26T15:54:33Z', '2021-10-28T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (10, 'Fake movie with review 10', '2001-10-12', TRUE, TRUE, '2021-10-26T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (4, 10, 'Some text for movie 10.', 10, '2021-10-26T15:54:33Z', '2021-10-29T15:54:33Z');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (10, 1010, 'tt1010', 'ko', 'A tagline!', 'An overview!', 10000, 100000, 'An URL 10', 101);
