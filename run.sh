#!/bin/bash
echo "Building WAR with maven..."
mvn clean install

docker-compose up --build --force-recreate -d
