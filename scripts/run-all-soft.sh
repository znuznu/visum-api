#!/bin/env bash
# Start all the containers

docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
