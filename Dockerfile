FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN javac ChatServer.java

FROM openjdk:17-jdk-slim

EXPOSE 1099

RUN mkdir -p /home/chat

COPY --from=build /target/chatrmi-jar-with-dependencies.jar chatrmi.jar


ENTRYPOINT [ "java", "-jar", "chatrmi.jar" ]