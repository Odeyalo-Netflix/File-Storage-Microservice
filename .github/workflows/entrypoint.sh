#!/bin/sh
echo "Remove all containers"
#docker stop $(docker ps -a -q)
#docker rm $(docker ps -a -q)

echo "Building Dockerfile"

docker image build -t "filestorage"
