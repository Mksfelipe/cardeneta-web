#!/bin/bash

docker compose down -v

docker-compose up > docker-compose.log
