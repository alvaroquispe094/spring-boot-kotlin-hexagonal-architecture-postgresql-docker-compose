FROM adoptopenjdk:11-jre-hotspot
MAINTAINER 4softwaredevelopers.com
ADD /build/libs/*.jar app.jar
EXPOSE 80
EXPOSE 443
EXPOSE 8080
ENV _JAVA_OPTIONS="-XX:MaxRAM=256m"
CMD java $_JAVA_OPTIONS -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.liquibase.url=$SPRING_LIQUIBASE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -Dspring.main.allow-bean-definition-overriding=$MANAGEMENT_METRICS_EXPORT_PROMETHEUS_ENABLED -Dspring.jpa.hibernate.ddl-auto=$DDL_AUTO -jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]