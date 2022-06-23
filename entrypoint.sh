#!/bin/sh
echo "Remove all containers"
#docker stop $(docker ps -a -q)
#docker rm $(docker ps -a -q)
echo "Setting up folders..."
mkdir -p /odeyalo/dev/microservices/filestorage/videos/
echo "Building Dockerfile"
cd ../../
ls
docker build -t ./github/file-storage-test-image .

