#
# Build stage
#
FROM gradle:6.9.1-alpine as build
WORKDIR /root
COPY . /root
RUN gradle build

#
# Package stage
#
FROM openjdk:8-jre-alpine as builder
WORKDIR /root
COPY --from=build /root/build/libs/*.jar root/app.jar
EXPOSE 80
EXPOSE 443
EXPOSE 8080

ENTRYPOINT ["java","-XX:MaxRAM=256m","-jar","root/app.jar"]