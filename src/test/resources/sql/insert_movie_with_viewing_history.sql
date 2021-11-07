INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (1, 'Fake movie', '2001-10-12', FALSE, FALSE);

INSERT INTO movie_viewing_history (id, movie_id, viewing_date)
values (1, 1, '2020-10-18');
