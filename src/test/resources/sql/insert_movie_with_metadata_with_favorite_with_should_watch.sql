INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (91, 'Fake movie with metadata 91', '2001-10-12', TRUE, TRUE, '2021-10-20T15:54:33Z');

INSERT INTO movie_metadata (movie_id, tmdb_id, imdb_id, original_language, tagline, overview, budget, revenue,
                            poster_url, runtime)
values (91, 500, 'tt5', 'en', 'A tagline!', 'An overview!', 9000, 14000, 'An URL', 123);
