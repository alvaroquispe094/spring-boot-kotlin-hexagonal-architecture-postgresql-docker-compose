#
# Build stage
#
FROM gradle:6.9.1-jdk11-hotspot as build
WORKDIR /root
COPY . /root
#USER root                # This changes default user to root
#RUN chown -R gradle /root # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"
RUN gradle clean build --stacktrace
#WORKDIR /app
#COPY . /app/myProject /app/
#USER root                # This changes default user to root
#RUN chown -R gradle /app # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"
#RUN gradle clean build --stacktrace
#
# Package stage
#
FROM openjdk:11-jre-slim as builder
WORKDIR /root
COPY --from=build /root/build/libs/*.jar app.jar
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