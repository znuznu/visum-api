#!/bin/env python3
# Generate a SQL script with random movies, actors, directors, genres, reviews, metadata and viewing history.

from random import getrandbits
from random import sample
from random import randrange
from datetime import date
from faker import Faker

fake = Faker()
Faker.seed(8080)

FILE_PATH = "src/main/resources/db/migration"
FILE_NAME = "V1000_0__insert_fake_data.sql"
TOTAL_MOVIES = 3000
TOTAL_ACTORS = 9000
TOTAL_DIRECTORS = 2500
GENRES = [
    "Action",
    "Adventure",
    "Animation",
    "Comedy",
    "Crime",
    "Documentary",
    "Drama",
    "Family",
    "Fantasy",
    "History",
    "Horror",
    "Music",
    "Mystery",
    "Romance",
    "Science Fiction",
    "Thriller",
    "TV Movie",
    "War",
    "Western"
]


def get_rnd_movie(id):
    release_date = fake.date_between(date(1920, 1, 1))
    is_favorite = "TRUE" if fake.boolean(chance_of_getting_true=25) else "FALSE"
    is_to_watch = "TRUE" if fake.boolean(chance_of_getting_true=25) else "FALSE"
    creation_date = fake.date_between(date(2014, 1, 1))

    return "INSERT INTO movie\nvalues (" + str(id) + ", 'Fake movie #" + str(id) + "', '" + str(
            release_date) + "', " + is_favorite + ", " + is_to_watch + ", '" + str(creation_date) + "');\n"


def get_rnd_movie_metadata(movie_id):
    tmdb_id = movie_id + 1
    imdb_id = "tt" + str(tmdb_id)
    localization = fake.language_code()
    tagline = fake.sentence(nb_words=9)
    overview = fake.text(max_nb_chars=800)
    budget = randrange(100000, 200000000)
    revenue = randrange(budget - 50000, 2000000000)
    poster_url = 'https://image.tmdb.org/t/p/w780/vfrQk5IPloGg1v9Rzbh2Eg3VGyM.jpg'
    runtime = randrange(30, 200)

    return "INSERT INTO movie_metadata\nvalues(" + str(movie_id) + ", " + str(
        tmdb_id) + ", '" + imdb_id + "', '" + localization + "', '" + tagline + "', '" + overview + "', " + str(
        budget) + ", " + str(revenue) + ", '" + poster_url + "', " + str(runtime) + ");\n"


def get_rnd_movie_review(id, movie_id):
    grade = randrange(1, 10)
    content = fake.text(max_nb_chars=1000)

    return "INSERT INTO movie_review\nvalues(" + str(id) + ", " + str(grade) + ", '" + content + "', " + str(
        movie_id) + ");\n"


def get_rnd_movie_viewing_history(starting_history_id, movie_id, nb_dates=1):
    dates = []

    current_date = fake.date_between(date(2020, 1, 1))

    for i in range(nb_dates):
        viewing_date = fake.date_between(current_date)
        query = "INSERT INTO movie_viewing_history\nvalues(" + str(starting_history_id + i) + ", " + str(
            movie_id) + ", '" + str(viewing_date) + "');\n"
        dates.append(query)

    return dates


with open(FILE_PATH + "/" + FILE_NAME, "w") as f:
    # Write a set of actors
    for i in range(1, TOTAL_ACTORS + 1):
        actor_query = "INSERT INTO actor\nvalues (" + str(
            i) + ", '" + fake.first_name() + " " + str(i)  + "', '" + fake.last_name() + "');\n"
        f.write(actor_query)

    f.write("\n")

    # Write a set of directors
    for i in range(1, TOTAL_DIRECTORS + 1):
        director_query = "INSERT INTO director\nvalues (" + str(
            i) + ", '" + fake.first_name() + " " + str(i)  +  "', '" + fake.last_name() + "');\n"
        f.write(director_query)

    f.write("\n")

    # Write a set of predefined genres
    for i, g in enumerate(GENRES):
        f.write("INSERT INTO genre\nvalues (" + str(i + 1) + ", '" + g + "');\n")

    f.write("\n")

    current_movie_review_id = 1
    current_movie_viewing_history_id = 1

    for m_id in range(1, TOTAL_MOVIES + 1):
        f.write(get_rnd_movie(m_id))

        # Links directors and actors to movie
        for a_id in sample(range(1, TOTAL_ACTORS + 1), 5):
            f.write("INSERT INTO movie_actor\nvalues (" + str(m_id) + ", " + str(a_id) + ");\n")

        d_id = randrange(1, TOTAL_DIRECTORS + 1)
        f.write("INSERT INTO movie_director\nvalues (" + str(m_id) + ", " + str(d_id) + ");\n")

        # Links genres to movie
        for g_id in sample(range(1, len(GENRES) + 1), randrange(1, 3)):
            f.write("INSERT INTO movie_genre\nvalues (" + str(m_id) + ", " + str(g_id) + ");\n")

        # Links metadata to the movie
        f.write(get_rnd_movie_metadata(m_id))

        # Links review to movie
        should_create_review = fake.boolean(chance_of_getting_true=25)

        if should_create_review:
            f.write(get_rnd_movie_review(current_movie_review_id, m_id))
            current_movie_review_id += 1

        # Links viewing history to the movie
        should_create_movie_viewing_history = fake.boolean(chance_of_getting_true=33)

        if should_create_movie_viewing_history:
            dates = get_rnd_movie_viewing_history(current_movie_viewing_history_id, m_id, randrange(1, 4))

            for d in dates:
                f.write(d)

            current_movie_viewing_history_id += len(dates)

        f.write("\n")
