#!/bin/env bash
# Recreate all the backend entities in their containers

docker-compose -f docker-compose.yml -f docker-compose.dev.yml up --build --force-recreate
