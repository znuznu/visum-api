-- Director
INSERT INTO director (id, name, forename, poster_url, tmdb_id)
values (1, 'Lynch', 'David', 'fake_url', 1234);

-- Actors
INSERT INTO actor (id, name, forename)
values (1, 'DiCaprio', 'Leonardo');
INSERT INTO actor (id, name, forename)
values (2, 'MacLachlan', 'Kyle');
INSERT INTO actor (id, name, forename)
values (3, 'Hardy', 'Tom');

-- Genres
INSERT INTO genre (id, type)
values (1, 'Action');
INSERT INTO genre (id, type)
values (2, 'Adult');
INSERT INTO genre (id, type)
values (3, 'Adventure');
