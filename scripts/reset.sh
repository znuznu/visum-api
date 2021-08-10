#!/bin/env bash
# Tiny script to clear all Docker related entities during dev
# /!\ CAUTION: all data will be deleted

docker rm -f visum-db visum-db-flyway visum-db-manage;
docker volume rm backend_visum-db;
docker network rm backend_visum-network;
