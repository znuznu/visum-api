#!/bin/env bash
# Start all the backend entities in their containers

docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
