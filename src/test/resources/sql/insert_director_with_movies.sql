INSERT INTO director (id, name, forename, tmdb_id, poster_url)
values (2, 'Nolan', 'Christopher', 1234, 'https://fakeurl.com');

INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (1, 'Fake movie 1', '2001-10-12', FALSE, FALSE);

INSERT INTO movie (id, title, release_date, is_favorite, should_watch)
values (2, 'Fake movie 2', '2006-06-20', FALSE, TRUE);

INSERT INTO movie_director (movie_id, director_id)
values (1, 2);

INSERT INTO movie_director (movie_id, director_id)
values (2, 2);
