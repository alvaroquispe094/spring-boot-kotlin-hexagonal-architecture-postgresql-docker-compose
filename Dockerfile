#
# Build stage
#
FROM gradle:6.9.1-jdk11-alpine as build
WORKDIR /root
COPY . /root
#USER root                # This changes default user to root
#RUN chown -R gradle /root # This changes ownership of folder
#USER gradle              # This changes the user back to the default user "gradle"
RUN gradle build
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
COPY --from=build /root/build/libs/*.jar root/app.jar
EXPOSE 8080

ENV DDL_AUTO = "update"
ENV MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED = "false"
ENV SPRING_PROFILES_ACTIVE="prod,api-docs"
ENV SPRING_DATASOURCE_PASSWORD = "j2rXgUCjb5B9AzeIziXEngZj8fiOh1ka"
ENV SPRING_DATASOURCE_URL = "jdbc:postgresql://dpg-cd610lmn6mprs1p6qvk0-a:5432/countries_docker_db"
ENV SPRING_DATASOURCE_USERNAME = "countries_docker_db_user"
ENV SPRING_LIQUIBASE_URL = "jdbc:postgresql://dpg-cd610lmn6mprs1p6qvk0-a:5432/countries_docker_db"
ENV SPRING_OPEN_IN_VIEW = "false"

CMD java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE $_JAVA_OPTIONS -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.liquibase.url=$SPRING_LIQUIBASE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -Dspring.jpa.hibernate.ddl-auto=$DDL_AUTO -jar root/app.jar
#ENTRYPOINT ["java","-jar","root/app.jar"]

#ENTRYPOINT ["java","-jar","app.jar"]





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