# Roadmap

## Refactor

[~] hexagonal architecture (without going too deep)  
[x] handle errors/exceptions  
[x] set up an OpenAPI spec  
[ ] refactor the pagination (broken?):  
    * Specification part  
    * GetPage IT tests  
[ ] refactor the signin usecase    
[ ] fix the tmdb route IT  
[ ] handle `org.springframework.web.HttpRequestMethodNotSupportedException`  
[ ] refactor entities' builders  
[ ] do something useful of the TMDb InMemory connector  
[ ] JPQL instead of native query where it's more convenient  
[ ] remove the useless \" in tests with JSON  
[ ] use a const variable for the API url in IT

## CI

[ ] set up a CI/CD with gh actions

## Features

[~] statistics (gql ?)  
[ ] posters (S3 ? TMDb url ?)
[ ] random movie 2 times per week, send by email  
[ ] worldmap to display movies by country   
