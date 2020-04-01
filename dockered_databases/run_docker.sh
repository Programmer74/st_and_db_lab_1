#!/bin/bash
mkdir -p ~/st_and_db_labs_data/oracle
mkdir -p ~/st_and_db_labs_data/mongo
mkdir -p ~/st_and_db_labs_data/mysql
mkdir -p ~/st_and_db_labs_data/postgre
docker-compose build
docker-compose up
