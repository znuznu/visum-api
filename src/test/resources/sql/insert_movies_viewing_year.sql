-- Movie 1

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (1, 'Fake movie 1', '2001-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 9, 'Some text 1', 1, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (1, 1, '2020-01-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (2, 1, '2019-12-31');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (1, 1111, 'tt1111', 'en', 'A tagline!', 'An overview!', 1000, 10000, 'An URL 1', 111);

-- Movie 2

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (2, 'Fake movie 2', '2002-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (2, 9, 'Some text 2', 2, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (3, 2, '2018-02-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (4, 2, '2020-02-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (2, 2222, 'tt2222', 'en', 'A tagline!', 'An overview!', 2000, 20000, 'An URL 2', 222);

-- Movie 3

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (3, 'Fake movie 3', '2003-10-12', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (3, 10, 'Some text 3', 3, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (5, 3, '2014-04-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (6, 3, '2019-07-06');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (3, 3333, 'tt3333', 'en', 'A tagline!', 'An overview!', 3000, 30000, 'An URL 3', 333);

-- Movie 4

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (15, 'Fake movie 15', '2014-10-12', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (15, 9, 'Some text 15', 15, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (11, 15, '2019-01-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (12, 15, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (15, 1515, 'tt1515', 'uk', 'A tagline!', 'An overview!', 15000, 150000, 'An URL 15', 151);

-- Genres

INSERT INTO genre
values (3, 'Adventure');

INSERT INTO genre
values (4, 'Animation');

INSERT INTO genre
values (5, 'Biography');

INSERT INTO genre
values (6, 'Comedy');

INSERT INTO genre
values (7, 'Crime');

-- Movie <> Genre

INSERT INTO movie_genre
values(1, 3);

INSERT INTO movie_genre
values(1, 4);

INSERT INTO movie_genre
values(2, 3);

INSERT INTO movie_genre
values(15, 7);
