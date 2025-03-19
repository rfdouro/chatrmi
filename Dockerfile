FROM ubuntu:latest AS build

#ARG java.rmi.server.hostname
#ENV java.rmi.server.hostname=${java.rmi.server.hostname}
ENV java.rmi.server.hostname='216.24.57.4'

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean compile assembly:single

FROM openjdk:17-jdk-slim

EXPOSE 1099

COPY --from=build /target/chatrmi-jar-with-dependencies.jar chatrmi.jar


ENTRYPOINT [ "java", "-jar", "chatrmi.jar" ]