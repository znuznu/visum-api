# Visum backend

This folder is the backend folder of _Visum_, based on __Spring Boot 2.3__ and __PostgreSQL 12.4__

## Requirements
* [Docker](https://www.docker.com/)
* [Docker-compose](https://docs.docker.com/compose/)
* [Java](https://openjdk.java.net/) >= 11 *
* [Maven](https://maven.apache.org/install.html) >= 3.6.0 *
* A [TMDb API token](https://www.themoviedb.org/documentation/api)

\* _If used outside Docker._

## Installation

Create a `.env` file based on the sample, fill it with your own variables.
 
You can fill the database with fake data by removing the `.sample` extension of the `*_data.sql.sample` files.
 
Run the desired `run-` scripts of the `scripts` folder, directly from this folder.

| Scripts                  | Description                                                                            |
|--------------------------|----------------------------------------------------------------------------------------|
| `./scripts/run-db.sh`      | Run the PostgreSQL container                                                           |
| `./scripts/run-all-hard.sh`| Recreate Spring Boot + PostgreSQL containers                                                |
| `./scripts/run-all-soft.sh`| Start Spring Boot + PostgreSQL containers                                                |
| `./scripts/reset.sh`       | Remove ALL Docker backend entities related to _Visum_ (containers, volumes, networks...) |
| `./scripts/wait-for-it.sh` | Don't touch or use it directly :)                                                      |
 
 This project uses the TMDb API, please be respectful of the [TMDb API Terms of use](https://www.themoviedb.org/documentation/api/terms-of-use) when trying _Visum_ on your own.
