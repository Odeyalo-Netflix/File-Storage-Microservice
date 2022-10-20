#!/bin/bash

# Bash script to bootstrap main infrastructure services such Kafka, Postgres in separated Docker containers.
# It also creates a network to communicate between docker containers.

GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RED='\033[0;31m'
RESET_COLOR="\e[0m"

function logInfo() {
  echo -e "[${GREEN} INFO ${RESET_COLOR}] $1"
}

function logWarn() {
  echo -e "[${YELLOW} WARN ${RESET_COLOR}] $1 "
}

function logError() {
  echo -e "[${RED} ERROR ${RESET_COLOR}] $1"
}

function rerunPostgres() {
  postgres_name=$1
  network_name=$2

  docker container stop "$postgres_name"

  docker container rm "$postgres_name"

  startPostgres "$postgres_name" "$network_name"

}

function startPostgres() {
  postgres_name=$1
  network_name=$2
  read -s -p "$(logInfo "Enter the password for postgres: ")" -r password

  docker run --net "$network_name" --name "$postgres_name" -e POSTGRES_PASSWORD="$password" -p 5432:5432 -d postgres:13.1-alpine
  retVal=$?
  if [ "$retVal" -ne 0 ]; then
    logError "Failed to bootstrap postgres. See logs to more info"
    exit 125
  fi
  logInfo "The container with name $postgres_name has been rebuilt and restarted with network: $network_name and password: $password"
}

network_name=$1
if [ -z "$network_name" ]; then
  read -p "$(logWarn "The network cannot be empty. Enter the network name: ")" -r
  network_name=$REPLY
  logInfo "Use network name: $network_name"
fi
if docker network ls | grep -q "$network_name"; then
  read -p "$(logWarn "The network with name $network_name already exist. Create a new one(y) or reuse existing one(n)?")" -n 1 -r
  echo ""
  #If reply is 'Y' or 'y' the script creates a new network with given name
  #Otherwise will be reused existing network

  if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker network rm "$network_name"
    retVal=$1
    if [ "$retVal" -nq 0 ]; then
      echo "Network deletion has been failed"
      exit 125
    fi
    docker network create "$network_name"
    logInfo "Deleted and created a new network with name: $network_name with default values"
  else
    logInfo "Reuse existing one"
  fi
else
  docker network create "$network_name"
  logInfo "Created docker network: $network_name since it doesn't exist"
fi

#Bootstrap postgres in docker.
postgres_name=postgres
logInfo "Starting $postgres_name container bootstrap"

if docker ps | grep -q "$postgres_name"; then
  read -p "$(logWarn "The container with name $postgres_name already is running. Do you wish to rebuild and restart container(y) or keep container running in this state(n)?")" -n 1 -r
  echo ""
  if [[ $REPLY =~ ^[Yy]$ ]]; then
    logInfo "Rebuild and restart $postgres_name container"
    rerunPostgres "$postgres_name" "$network_name"
  else
    logInfo "No recreate $postgres_name container"
  fi
else
  logInfo "Postgres is not running."
  if docker ps -a | grep -q $postgres_name; then
    logInfo "The container with name: $postgres_name already exist. Reusing existing container. If you want to create a new one you need to delete existing container with name $postgres_name and rerun the script"
    docker container start $postgres_name
  else
    startPostgres "$postgres_name" "$network_name"
  fi
fi

# Bootstrap kafka broker in docker
logInfo "Starting kafka bootstrap"

export NETWORK_NAME=$network_name

if docker ps | grep -q "kafka"; then
  logInfo "Kafka is already running. Bootstrap skipped"
else
  docker-compose -f kafka-docker-compose.yml up -d
  logInfo "Kafka is bootstrapping in the detached mode"
fi

logInfo "Services: $postgres_name and kafka has been successfully started!"
