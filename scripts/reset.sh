#!/bin/env bash
# Tiny script to clear all Docker related entities during dev
# /!\ CAUTION: all data will be deleted

docker rm -f visum-db visum-db-flyway visum-backend;
docker volume rm visum-api_visum-db;
docker network rm visum-api_visum-network;
