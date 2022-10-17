# Dockerfile focused on production use case
# Builder stage needs JDK and gradle
FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR /root
COPY . .
RUN ./gradlew build

# Runner stage only needs JRE and JAR
FROM adoptopenjdk:11-jre-hotspot
WORKDIR /root
EXPOSE 80
EXPOSE 443
EXPOSE 8080
COPY --from=builder /root/build/libs/*.jar ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]