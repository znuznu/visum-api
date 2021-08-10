-- Initialize some movies for testing purposes

-- Actor
INSERT INTO actor values (1, 'DiCaprio', 'Leonardo');
INSERT INTO actor values (2, 'Kyle', 'MacLachlan');
INSERT INTO actor values (3, 'Hardy', 'Tom');
INSERT INTO actor values (4, 'De Niro', 'Robert');
INSERT INTO actor values (5, 'Mitchell', 'Radha');
INSERT INTO actor values (6, 'Bean', 'Sean');
INSERT INTO actor values (7, 'Robbins', 'Tim');

-- Director
INSERT INTO director values (1, 'Gans', 'Christopher');
INSERT INTO director values (2, 'Lyne', 'Adrian');
INSERT INTO director values (3, 'González Iñárritu', 'Alejandro');
INSERT INTO director values (4, 'Winding Refn', 'Nicolas');

-- Movie
INSERT INTO movie values (1, 'Jacobs Ladder', '1990-11-02', FALSE, FALSE);
INSERT INTO movie values (2, 'Silent Hill', '2006-04-26', FALSE, FALSE);
INSERT INTO movie values (3, 'The Revenant', '2016-01-08', FALSE, FALSE);
INSERT INTO movie values (4, 'Bronson', '2009-07-15', FALSE, FALSE);
INSERT INTO movie values (5, 'Movie random 5', '2009-07-15', FALSE, FALSE);
INSERT INTO movie values (6, 'Movie random 6', '2009-07-16', FALSE, FALSE);
INSERT INTO movie values (7, 'Movie random 7', '2009-07-17', FALSE, FALSE);
INSERT INTO movie values (8, 'Movie random 8', '2009-07-18', FALSE, FALSE);
INSERT INTO movie values (9, 'Movie random 9', '2009-07-19', FALSE, FALSE);
INSERT INTO movie values (10, 'Movie random 10', '2010-07-20', FALSE, FALSE);
INSERT INTO movie values (11, 'Movie random 11', '2011-07-20', FALSE, FALSE);
INSERT INTO movie values (12, 'Movie random 12', '2012-07-20', FALSE, FALSE);
INSERT INTO movie values (13, 'Movie random 13', '2013-07-20', FALSE, FALSE);
INSERT INTO movie values (14, 'Movie random 14', '2014-07-20', FALSE, FALSE);
INSERT INTO movie values (15, 'Movie random 15', '2015-07-20', FALSE, FALSE);
INSERT INTO movie values (16, 'Movie random 16', '2016-07-20', FALSE, FALSE);
INSERT INTO movie values (17, 'Movie random 17', '2017-07-20', FALSE, FALSE);
INSERT INTO movie values (18, 'Movie random 18', '2018-07-20', FALSE, FALSE);
INSERT INTO movie values (19, 'Movie random 19', '2019-07-20', FALSE, FALSE);
INSERT INTO movie values (20, 'Movie random 20', '2020-07-20', FALSE, FALSE);
INSERT INTO movie values (21, 'Movie random 21', '2021-07-20', FALSE, FALSE);
INSERT INTO movie values (22, 'Movie random 22', '2022-07-20', FALSE, FALSE);
INSERT INTO movie values (23, 'Movie random 23', '2023-07-20', FALSE, FALSE);
INSERT INTO movie values (24, 'Movie random 24', '2024-07-20', FALSE, FALSE);
INSERT INTO movie values (25, 'Movie random 25', '2025-07-20', FALSE, FALSE);
INSERT INTO movie values (26, 'Movie random 26', '2026-07-20', FALSE, FALSE);
INSERT INTO movie values (27, 'Movie random 27', '2027-07-20', FALSE, FALSE);

-- Movie & Actor
INSERT INTO movie_actor values (3, 3);
INSERT INTO movie_actor values (3, 1);
INSERT INTO movie_actor values (2, 5);
INSERT INTO movie_actor values (2, 6);
INSERT INTO movie_actor values (1, 7);
INSERT INTO movie_actor values (4, 3);

-- Movie & Director
INSERT INTO movie_director values (1, 1);
INSERT INTO movie_director values (2, 2);
INSERT INTO movie_director values (3, 3);
INSERT INTO movie_director values (4, 4);

-- Movie & Genre
INSERT INTO movie_genre values (1, 15);
INSERT INTO movie_genre values (1, 9);
INSERT INTO movie_genre values (2, 15);
INSERT INTO movie_genre values (3, 28);
INSERT INTO movie_genre values (4, 5);

-- Movie review
INSERT INTO movie_review values(1, 9, 'An interesting movie about ladder.', 1);
INSERT INTO movie_review values(2, 4, 'Cult, death, blood, blurp.', 2);
INSERT INTO movie_review values(3, 9, 'Bang bang bang in the snow.', 3);
INSERT INTO movie_review values(4, 9, 'Bluh blah bleh.', 4);
INSERT INTO movie_review values(5, 9, 'Bluh blah bleh.', 5);
INSERT INTO movie_review values(6, 9, 'Bluh blah bleh.', 6);
INSERT INTO movie_review values(7, 9, 'Bluh blah bleh.', 7);
INSERT INTO movie_review values(8, 9, 'Bluh blah bleh.', 8);
INSERT INTO movie_review values(9, 9, 'Bluh blah bleh.', 9);
INSERT INTO movie_review values(10, 9, 'Bluh blah bleh.', 10);
INSERT INTO movie_review values(11, 9, 'Bluh blah bleh.', 11);
INSERT INTO movie_review values(12, 9, 'Bluh blah bleh.', 12);
INSERT INTO movie_review values(13, 9, 'Bluh blah bleh.', 13);
INSERT INTO movie_review values(14, 9, 'Bluh blah bleh.', 14);
INSERT INTO movie_review values(15, 9, 'Bluh blah bleh.', 15);
INSERT INTO movie_review values(16, 9, 'Bluh blah bleh.', 16);
INSERT INTO movie_review values(17, 9, 'Bluh blah bleh.', 17);
INSERT INTO movie_review values(18, 9, 'Bluh blah bleh.', 18);
INSERT INTO movie_review values(19, 9, 'Bluh blah bleh.', 19);
INSERT INTO movie_review values(20, 9, 'Bluh blah bleh.', 20);
INSERT INTO movie_review values(21, 9, 'Bluh blah bleh.', 21);
INSERT INTO movie_review values(22, 9, 'Bluh blah bleh.', 22);
INSERT INTO movie_review values(23, 9, 'Bluh blah bleh.', 23);
INSERT INTO movie_review values(24, 9, 'Bluh blah bleh.', 24);
