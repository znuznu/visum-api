# Changelog

## 2.3.0

* Add TMDb poster's URL in the `/tmdb/movies/search` and `/tmdb/movies/<id>` responses
* Add `String getConfigurationBasePosterUrl();` to the `TmdbConnector` port and the implementation in the HTTP adapter
* Add `SearchTmdbMoviesServiceImplUnitTest`
* Rename `external` package to `externals`
* Add [Caffeine](https://github.com/ben-manes/caffeine) in order to cache the TMDb (base) poster's URL with `@Cacheable`

## 2.2.1

* Add pagination usecases IT
* Move Spring Specification related code to the infrastructure
* Fix the pagination search problem by using a `Specification<xxxEntity>`

## 2.2.0

* Add statistics:
    - _get all-time_ and _get per year_ usecases
    - add two routes: `/statistics/years` and `/statistics/years/<year>`

## 2.1.1

* Replace deprecated method `isEqualToComparingFieldByField()` with `usingRecursiveComparison()`
* Remove `hashCode`and `equals` on related models

## 2.1.0

* Bump Spring Boot from 2.3.4 to 2.5.6
* Add a TODO `withPage()` to PageSearch
* Bump Auth0 JWT from 3.13.0 to 3.18.2
* Bump Spring HATEOAS from 1.1.0 to 1.3.5
* Bump Testcontainers from 1.16.0 to 1.16.2

## 2.0.0

* Refactor the whole API architecture to a clean one (without going too deep)
* Add tests
* Add OpenAPI (Springfox)
* Remove adminer from the docker-compose
* Rename `visum-backend` to `visum-api`
* Add Github Actions to build/verify and push to Docker hub registry on SemVer tags
