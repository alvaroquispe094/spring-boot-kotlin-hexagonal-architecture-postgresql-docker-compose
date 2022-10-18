#
# Build stage
#
FROM gradle:4.4-jdk11
COPY . .
USER root                # This changes default user to root
RUN chown -R gradle . # This changes ownership of folder
USER gradle              # This changes the user back to the default user "gradle"
RUN gradle clean build
#
# Package stage
#
FROM openjdk:11-jre-slim
ADD /build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]





# Dockerfile focused on production use case
# Builder stage needs JDK and gradle
#FROM openjdk:11 as builder
#WORKDIR /root
#COPY . /root
#USER root                # This changes default user to root
#RUN chown -R gradle /root # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"

#RUN ./gradlew build --stacktrace

# Runner stage only needs JRE and JA