FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/gcp-bq-authorized-view-demo-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} gcp-bq-authorized-view-demo.war
ENTRYPOINT ["java","-jar","/gcp-bq-authorized-view-demo.war"]