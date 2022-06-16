FROM maven:3.8.5-jdk-11-slim

WORKDIR file-storage
COPY /../../ /app

ENTRYPOINT ["mvn", "install"]
ENTRYPOINT ["tail", "-f", "/dev/null"]

