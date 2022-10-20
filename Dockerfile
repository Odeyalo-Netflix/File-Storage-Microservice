FROM maven:3.8.5-jdk-11-slim

WORKDIR file-storage

COPY . .

RUN apt-get -y update
RUN apt-get install -y ffmpeg

ENTRYPOINT mvn -s maven-settings.xml spring-boot:run
