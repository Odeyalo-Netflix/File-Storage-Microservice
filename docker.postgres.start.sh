#!/bin/sh

container_name=postgres
network_name=netflix
docker container stop $container_name

docker container rm $container_name

echo "Deleted container with name: $container_name"

docker run --net netflix --name $container_name -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:13.1-alpine
