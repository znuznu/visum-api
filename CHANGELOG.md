# Changelog

## 2.13.1

- Bump TestContainers from 1.16.2 to 1.17.3
- Bump Springdoc from 1.6.6 to 1.6.9
- Bump MockWebServer from 4.9.2 to 4.10.0
- Bump OkHttp from 4.9.2 to 4.10.0
- Bump Spring HATEOAS from 1.3.5 to 1.5.1
- Bump Caffeine from 3.0.5 to 3.1.1
- Bump DGS from 4.9.22 to 5.0.5

## 2.13.0

- Add `creation_date` to viewing history
- Drastically improve viewing history and diary part

## 2.12.0

- Add `cast_member` table
- Remove `movie_actor` table
- Improve tests (naming, fixtures, constants, ...)
- Various classes renaming

## 2.11.3

- Remove the `Service` prefixes from the usecases

## 2.11.2

- Add `VisumAssert`
- Add `Content`
- Add `CreateReviewCommand` and `UpdateReviewCommand`

## 2.11.1

- Add `Identity` and `Grade`
- Rename `errors` -> `exceptions`

## 2.11.0

- The movie creation request is only based on the TMDb movie id
- Retrieves the configuration root poster URL inside the connector's method

## 2.10.0

- Add `posterUrl` and `tmdbId` to `Actor`

## 2.9.0

- Add `posterUrl` and `tmdbId` to `Director`
- Remove the unique `title` and `releaseDate` constraints on `Movie` (`tmdbId` used instead)

## 2.8.1

- Set the update movie metadata job cron value as a Spring expression

## 2.8.0

- Add `findAll()` to MovieRepository
- Add spring-boot-starter-batch
- Add `UpdateMovieMetadataJobLauncher` to update movie metadata periodically with Spring Batch

## 2.7.3

- Use Lombok for all builders

## 2.7.2

- Sort by grade the highest rated movies (older) in per year statistics

## 2.7.1

- Use Lombok

## 2.7.0

- Add GET `/tmdb/movies/upcoming` endpoint

## 2.6.3

- Remove TMDb cast duplicates before passing it to the domain layer

## 2.6.2

- Bump Spring Boot to 2.6.6
- Remove Springfox
- Add Springdoc 1.6.6

## 2.6.1

- Fix NPE in case of null viewing date

## 2.6.0

- Add GraphQL dependencies
- Add GraphQL `diary` query

## 2.5.1

- Fix typos
- Add `launch.json` file to `.vscode/`

## 2.5.0

- Add PUT `/movies/:id/favorite` endpoint
- Add DELETE `/movies/:id/favorite` endpoint
- Add PUT `/movies/:id/watchlist` endpoint
- Add DELETE `/movies/:id/watchlist` endpoint

## 2.4.0

- Add poster's URL to Movies and Reviews pages

## 2.3.0

- Add TMDb poster's URL in the `/tmdb/movies/search` and `/tmdb/movies/<id>` responses
- Add `String getConfigurationBasePosterUrl();` to the `TmdbConnector` port and the implementation in the HTTP adapter
- Add `SearchTmdbMoviesServiceUnitTest`
- Rename `external` package to `externals`
- Add [Caffeine](https://github.com/ben-manes/caffeine) in order to cache the TMDb (base) poster's URL with `@Cacheable`

## 2.2.1

- Add pagination usecases IT
- Move Spring Specification related code to the infrastructure
- Fix the pagination search problem by using a `Specification<xxxEntity>`

## 2.2.0

- Add statistics:
  - _get all-time_ and _get per year_ usecases
  - add two routes: `/statistics/years` and `/statistics/years/<year>`

## 2.1.1

- Replace deprecated method `isEqualToComparingFieldByField()` with `usingRecursiveComparison()`
- Remove `hashCode`and `equals` on related models

## 2.1.0

- Bump Spring Boot from 2.3.4 to 2.5.6
- Add a TODO `withPage()` to PageSearch
- Bump Auth0 JWT from 3.13.0 to 3.18.2
- Bump Spring HATEOAS from 1.1.0 to 1.3.5
- Bump Testcontainers from 1.16.0 to 1.16.2

## 2.0.0

- Refactor the whole API architecture to a clean one (without going too deep)
- Add tests
- Add OpenAPI (Springfox)
- Remove adminer from the docker-compose
- Rename `visum-backend` to `visum-api`
- Add Github Actions to build/verify and push to Docker hub registry on SemVer tags
