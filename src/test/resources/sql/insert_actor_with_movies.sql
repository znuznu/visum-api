INSERT INTO actor (id, name, forename)
values (2, 'Winslet', 'Kate');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (1, 'Fake movie 1', '2001-10-12', FALSE, FALSE);

INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (2, 'Fake movie 2', '2006-06-20', FALSE, TRUE);

INSERT INTO movie_actor (movie_id, actor_id)
values (1, 2);

INSERT INTO movie_actor (movie_id, actor_id)
values (2, 2);
