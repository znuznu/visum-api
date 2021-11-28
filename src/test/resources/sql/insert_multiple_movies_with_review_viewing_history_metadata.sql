-- Movie 1

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (1, 'Fake movie 1', '2001-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 9, 'Some text 1', 1, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (1, 1, '2020-01-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (2, 1, '2020-01-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (1, 1111, 'tt1111', 'en', 'A tagline!', 'An overview!', 1000, 10000, 'An URL 1', 111);

-- Movie 2

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (2, 'Fake movie 2', '2002-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (2, 1, 'Some text 2', 2, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (3, 2, '2020-02-01');

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
values (5, 3, '2020-04-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (6, 3, '2020-05-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (3, 3333, 'tt3333', 'en', 'A tagline!', 'An overview!', 3000, 30000, 'An URL 3', 333);

-- Movie 4

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (4, 'Fake movie 4', '2003-01-01', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (4, 8, 'Some text 4', 4, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (33, 4, '2020-04-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (34, 4, '2020-05-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (4, 4444, 'tt4444', 'en', 'A tagline!', 'An overview!', 4000, 40000, 'An URL 4', 444);

-- Movie 7

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (7, 'Fake movie 7', '2007-10-12', TRUE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (7, 4, 'Some text 7', 7, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (7, 7, '2020-05-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (8, 7, '2020-06-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (7, 7777, 'tt7777', 'de', 'A tagline!', 'An overview!', 7000, 70000, 'An URL 7', 777);

-- Movie 8

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (8, 'Fake movie 8', '2007-10-12', TRUE, FALSE, '2015-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (8, 6, 'Some text 8', 8, '2015-10-26T15:54:33Z', '2015-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (100, 8, '2020-05-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (101, 8, '2020-06-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (8, 8888, 'tt8888', 'de', 'A tagline!', 'An overview!', 8000, 80000, 'An URL 8', 888);

-- Movie 14

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (14, 'Fake movie 14', '2014-10-12', FALSE, FALSE, '2015-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (14, 1, 'Some text 14', 14, '2015-10-26T15:54:33Z', '2015-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (9, 14, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (10, 14, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (14, 1414, 'tt1414', 'jp', 'A tagline!', 'An overview!', 14000, 140000, 'An URL 14', 141);

-- Movie 15

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (15, 'Fake movie 15', '2014-10-12', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (15, 3, 'Some text 15', 15, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (11, 15, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (12, 15, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (15, 1515, 'tt1515', 'uk', 'A tagline!', 'An overview!', 15000, 150000, 'An URL 15', 151);

-- Movie 16

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (16, 'Fake movie 16', '2014-01-01', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (16, 6, 'Some text 16', 16, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (13, 16, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (14, 16, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (16, 1616, 'tt1616', 'jp', 'A tagline!', 'An overview!', 16000, 160000, 'An URL 16', 161);

-- Movie 17

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (17, 'Fake movie 17', '2014-10-12', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (17, 3, 'Some text 17', 17, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (15, 17, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (16, 17, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (17, 1717, 'tt1717', 'uk', 'A tagline!', 'An overview!', 17000, 170000, 'An URL 17', 171);

-- Movie 18

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (18, 'Fake movie 18', '2015-01-01', FALSE, FALSE, '2015-10-20T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (18, 4, 'Some text 18', 18, '2015-10-26T15:54:33Z', '2015-10-27T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (17, 18, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (18, 18, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (18, 1818, 'tt1818', 'uk', 'A tagline!', 'An overview!', 18000, 180000, 'An URL 18', 181);

-- Movie 19 (no review)

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (19, 'Fake movie 19', '2015-05-01', FALSE, FALSE, '2021-10-20T15:54:33Z');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (19, 19, '2020-07-01');

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (20, 19, '2020-08-02');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (19, 1919, 'tt1919', 'uk', 'A tagline!', 'An overview!', 19000, 190000, 'An URL 19', 191);
