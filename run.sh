#!/bin/bash
echo "Docker RabbitMQ..."
docker-compose up --build --force-recreate -d

echo "Building..."
mvn clean install

echo "Execute..."
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/votos-0.0.1-SNAPSHOT.jar
