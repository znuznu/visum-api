INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (30, 'Fake movie with review 30', '2001-10-12', FALSE, FALSE, '2021-10-26T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (1, 3, 'Some text for movie 30.', 30, '2021-10-26T15:54:33Z', '2021-10-26T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (20, 'Fake movie with review 20', '2001-10-12', TRUE, FALSE, '2021-10-26T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (2, 2, 'Some text for movie 20.', 20, '2021-10-26T15:54:33Z', '2021-10-27T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (33, 'Fake movie with review 33', '2001-10-12', FALSE, TRUE, '2021-10-26T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (3, 5, 'Some text for movie 33.', 33, '2021-10-26T15:54:33Z', '2021-10-28T15:54:33Z');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch, creation_date)
values (10, 'Fake movie with review 10', '2001-10-12', TRUE, TRUE, '2021-10-26T15:54:33Z');

INSERT INTO movie_review (id, grade, content, movie_id, creation_date, update_date)
values (4, 10, 'Some text for movie 10.', 10, '2021-10-26T15:54:33Z', '2021-10-29T15:54:33Z');
