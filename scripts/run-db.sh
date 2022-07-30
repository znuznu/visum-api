#!/bin/env bash
# Start the database container only

docker-compose -f docker-compose.yml -f docker-compose.dev.yml up visum-db
