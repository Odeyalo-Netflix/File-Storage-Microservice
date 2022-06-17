#!/bin/sh
echo "Remove all containers"
#docker stop $(docker ps -a -q)
#docker rm $(docker ps -a -q)
echo "Setting up folders..."
mkdir -p /odeyalo/dev/microservices/filestorage/videos/
echo "Building Dockerfile"

docker build -t file-storage-test-image ./../

