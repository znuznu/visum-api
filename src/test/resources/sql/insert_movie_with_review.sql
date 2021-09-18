INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (30, 'Fake movie with review', '2001-10-12', FALSE, FALSE);

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 3, 'Some text.', 30, '2021-10-26T15:54:33Z', '2021-10-26T15:54:33Z');
