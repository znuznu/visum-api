#!/bin/env bash
# Start the database related containers only to be able to run Spring Boot manually in dev

docker-compose -f docker-compose.yml -f docker-compose.dev.yml up visum-db flyway
