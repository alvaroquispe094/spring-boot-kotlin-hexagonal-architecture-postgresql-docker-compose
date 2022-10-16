FROM adoptopenjdk:11-jre-hotspot
MAINTAINER 4softwaredevelopers.com
ADD /build/libs/*.jar app.jar
EXPOSE 80
EXPOSE 443
EXPOSE 8080
ENV _JAVA_OPTIONS="-XX:MaxRAM=256m"
CMD java -jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]