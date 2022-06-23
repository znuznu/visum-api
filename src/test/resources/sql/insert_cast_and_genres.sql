-- Director
INSERT INTO director (id, name, forename, poster_url, tmdb_id)
values (1, 'Lynch', 'David', 'fake_url', 1234);

-- Actors
INSERT INTO actor (id, name, forename, poster_url, tmdb_id)
values (1, 'DiCaprio', 'Leonardo', 'fake_url111', 111);
INSERT INTO actor (id, name, forename, poster_url, tmdb_id)
values (2, 'MacLachlan', 'Kyle', 'fake_url222', 222);
INSERT INTO actor (id, name, forename, poster_url, tmdb_id)
values (3, 'Hardy', 'Tom', 'fake_url333', 333);

-- Genres
INSERT INTO genre (id, type)
values (1, 'Action');
INSERT INTO genre (id, type)
values (2, 'Adult');
INSERT INTO genre (id, type)
values (3, 'Adventure');
