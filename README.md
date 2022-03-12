# Visum API

This repo contains the API of _Visum_, based on __Spring Boot 2.5__ and __PostgreSQL 12.4__

## Requirements
* [Docker](https://www.docker.com/)
* [Docker-compose](https://docs.docker.com/compose/)
* [Java](https://openjdk.java.net/) >= 11 *
* [Maven](https://maven.apache.org/install.html) >= 3.6.0 *
* A [TMDb API token](https://www.themoviedb.org/documentation/api)
* [Python](https://www.python.org/) >= 3 with [Faker](https://faker.readthedocs.io/en/master/) installed **

\* _If used outside Docker._  
\** _If you want to generate fake SQL data during development._

## Installation

Create a `.env` file based on the sample, fill it with your own variables.
 
Run the desired `run-` scripts of the `scripts` folder, directly from this folder.

| Scripts                  | Description                                                                            |
|--------------------------|----------------------------------------------------------------------------------------|
| `./scripts/run-db.sh`      | Run the PostgreSQL container                                                         |
| `./scripts/run-all-hard.sh`| Recreate Spring Boot + PostgreSQL containers                                         |
| `./scripts/run-all-soft.sh`| Start Spring Boot + PostgreSQL containers                                            |
| `./scripts/reset.sh`       | Remove **ALL** Docker entities related to _Visum_ (containers, volumes, networks...) |
| `./scripts/wait-for-it.sh` | Don't touch or use it directly :)                                                    |
| `./scripts/fake_data.py`   | Generate an SQL script file with fake data                                           |
 
 This project uses the TMDb API, please be respectful of the [TMDb API Terms of use](https://www.themoviedb.org/documentation/api/terms-of-use) when you try _Visum_ on your own.
